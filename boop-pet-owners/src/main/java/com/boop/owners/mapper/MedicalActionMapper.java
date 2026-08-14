package com.boop.owners.mapper;

import com.boop.mapper.ResponseMapper;
import com.boop.owners.dto.MedicalActionResponse;
import com.boop.owners.persistence.entity.MedicalAction;
import org.springframework.stereotype.Component;

@Component
public class MedicalActionMapper implements ResponseMapper<MedicalActionResponse, MedicalAction> {

    @Override
    public MedicalActionResponse toResponse(MedicalAction medicalAction) {
        return new MedicalActionResponse(
                medicalAction.getMedicalActionType(),
                medicalAction.getDescription(),
                medicalAction.getActionDate(),
                medicalAction.getExpirationDate()
        );
    }
}
