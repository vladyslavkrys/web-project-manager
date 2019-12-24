package com.spring.manager.service;

import com.spring.manager.domain.Project;
import com.spring.manager.exception.ProjectIdentifierException;
import com.spring.manager.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdentifierException("Project ID '".concat(project.getProjectIdentifier().toUpperCase().concat("' already exists")));
        }
    }

    public Project findProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdentifierException("Project ID '".concat(projectId).concat("' does not exist"));
        }

        return project;
    }

    public Iterable<Project> findAll() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdentifierException("Cannot delete project with ID '".concat(projectId).concat("'. This project does not exist"));
        }

        projectRepository.delete(project);
    }
}
