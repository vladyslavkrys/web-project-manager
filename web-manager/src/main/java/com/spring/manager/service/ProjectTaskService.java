package com.spring.manager.service;

import com.spring.manager.domain.Backlog;
import com.spring.manager.domain.Project;
import com.spring.manager.domain.ProjectTask;
import com.spring.manager.exception.ProjectNotFoundException;
import com.spring.manager.repository.BacklogRepository;
import com.spring.manager.repository.ProjectRepository;
import com.spring.manager.repository.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private BacklogRepository backlogRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {
        //exceptions: project not found
        try {
            //PTs to be added to a specific project, project != null, BL exists
            Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();
            //set the BL to PT
            projectTask.setBacklog(backlog);
            //we wanna our project sequence like this: IDPRO-1 / IDPRO-2 ...100
            Integer backlogSequence = backlog.getPTSequence();
            //update the BL SEQUENCE
            backlog.setPTSequence(++backlogSequence);
            //add sequence to project task
            projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            //INITIAL PRIORITY when priority null
            if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
                projectTask.setPriority(3);
            }
            //INITIAL status when status is null
            if ("".equals(projectTask.getStatus()) || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);
        } catch (Exception e) {
            throw new ProjectNotFoundException("Project not found");
        }
    }

    public Iterable<ProjectTask> findBacklogById(String id, String username) {
        projectService.findProjectByIdentifier(id, username);

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username) {
        // make sure we are searching on existing backlog
        projectService.findProjectByIdentifier(backlog_id, username);

        // make sure that our task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if (projectTask == null) {
            throw new ProjectNotFoundException("Project Task '".concat(pt_id).concat("' not found."));
        }

        // make sure that the backlog/project id in the path corresponds to the right project
        if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
            throw new ProjectNotFoundException("Project Task '".concat(pt_id).concat("' does not exist in project: '").concat(backlog_id));
        }

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String username) {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

        return projectTaskRepository.save(projectTask != null ? updatedTask : null);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id, String username) {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

        projectTaskRepository.delete(projectTask);
    }
}
