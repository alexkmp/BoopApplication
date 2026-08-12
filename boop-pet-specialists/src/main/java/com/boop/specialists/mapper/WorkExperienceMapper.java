package com.boop.specialists.mapper;

import com.boop.mapper.ResponseMapper;
import com.boop.specialists.dto.WorkExperienceResponse;
import com.boop.specialists.persistence.entity.WorkExperience;
import org.springframework.stereotype.Component;

@Component
public class WorkExperienceMapper implements ResponseMapper<WorkExperienceResponse, WorkExperience> {

    @Override
    public WorkExperienceResponse toResponse(WorkExperience workExperience) {
        return new WorkExperienceResponse(
                workExperience.getCompanyName(),
                workExperience.getJobTitle(),
                workExperience.getJobDuration()
        );
    }
}
