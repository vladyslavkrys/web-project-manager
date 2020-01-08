package com.spring.manager.service;

import com.spring.manager.domain.Backlog;
import com.spring.manager.domain.Project;
import com.spring.manager.domain.User;
import com.spring.manager.exception.ProjectIdentifierException;
import com.spring.manager.exception.ProjectNotFoundException;
import com.spring.manager.repository.BacklogRepository;
import com.spring.manager.repository.ProjectRepository;
import com.spring.manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project, String username) {
        if (project.getId() != null) {
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
            if (existingProject != null && !existingProject.getProjectLeader().equals(username)) {
                throw new ProjectNotFoundException("Project not found in your account");
            } else if (existingProject == null) {
                throw new ProjectNotFoundException("Project with ID: '" + project.getProjectIdentifier() + "' cannot be updated because it doesn't exist");
            }
        }

        String projectIdentifier = project.getProjectIdentifier().toUpperCase();
        try {
            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(projectIdentifier);
            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(projectIdentifier);
            }

            if (project.getId() != null) {
                project.setBacklog(backlogRepository.findByProjectIdentifier(projectIdentifier));
            }

            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdentifierException("Project ID '".concat(projectIdentifier.concat("' already exists")));
        }
    }

    public Project findProjectByIdentifier(String projectId, String username) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null) {
            throw new ProjectIdentifierException("Project ID '".concat(projectId).concat("' does not exist"));
        }

        if (!project.getProjectLeader().equals(username)) {
            throw new ProjectIdentifierException("Project not found in your account");
        }

        return project;
    }

    public Iterable<Project> findAll(String username) {
        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectId, String username) {
        projectRepository.delete(findProjectByIdentifier(projectId, username));
    }
}
