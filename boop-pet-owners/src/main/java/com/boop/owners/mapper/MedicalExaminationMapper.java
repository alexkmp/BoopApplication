package com.boop.owners.mapper;

import com.boop.mapper.ResponseMapper;
import com.boop.owners.dto.MedicalExaminationResponse;
import com.boop.owners.persistence.entity.MedicalExamination;
import org.springframework.stereotype.Component;

@Component
public class MedicalExaminationMapper implements ResponseMapper<MedicalExaminationResponse, MedicalExamination> {

    @Override
    public MedicalExaminationResponse toResponse(MedicalExamination medicalExamination) {
        return new MedicalExaminationResponse(
                medicalExamination.getExaminationDate(),
                medicalExamination.getExpirationDate(),
                medicalExamination.getReport()
        );
    }
}
