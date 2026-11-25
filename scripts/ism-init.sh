#!/bin/sh
set -eu

OPENSEARCH_URL="${OPENSEARCH_URL:-http://opensearch:9200}"
POLICY_ID="logs-retention"
POLICY_FILE="/tmp/ism-policy.json"
PATTERN="app-logs-*"

echo "[ism-init] Waiting for OpenSearch at ${OPENSEARCH_URL} ..."
until curl -s -o /dev/null -w "%{http_code}" "${OPENSEARCH_URL}" | grep -q "200"; do
  sleep 2
done
echo "[ism-init] OpenSearch is up."

# --- 1. create or update policy ---
echo "[ism-init] Creating/updating ISM policy '${POLICY_ID}' ..."
HTTP=$(curl -s -o /tmp/resp.json -w "%{http_code}" \
  -X PUT "${OPENSEARCH_URL}/_plugins/_ism/policies/${POLICY_ID}" \
  -H "Content-Type: application/json" \
  -d @"${POLICY_FILE}")
echo "[ism-init] Policy response: HTTP ${HTTP}"
cat /tmp/resp.json; echo

if [ "${HTTP}" != "200" ] && [ "${HTTP}" != "201" ]; then
  echo "[ism-init] ERROR: failed to create/update policy"; exit 1
fi

# --- 2. bind policy to current indices by pattern ---
echo "[ism-init] Attaching policy to existing indices matching '${PATTERN}' ..."
INDEXES=$(curl -s "${OPENSEARCH_URL}/_cat/indices/${PATTERN}?h=index" || true)

if [ -z "${INDEXES}" ]; then
  echo "[ism-init] No existing indices found. Nothing to attach."
else
  for idx in ${INDEXES}; do
    echo "[ism-init]   -> ${idx}"
    curl -s -X POST "${OPENSEARCH_URL}/_plugins/_ism/add/${idx}" \
      -H "Content-Type: application/json" \
      -d "{\"policy_id\":\"${POLICY_ID}\"}" >/dev/null
  done
fi

echo "[ism-init] Done. ISM policy '${POLICY_ID}' is active."