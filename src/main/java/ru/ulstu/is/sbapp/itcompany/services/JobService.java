package ru.ulstu.is.sbapp.itcompany.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.itcompany.models.job.JobDTO;
import ru.ulstu.is.sbapp.itcompany.models.job.Job;
import ru.ulstu.is.sbapp.itcompany.repositories.JobRepository;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    @PersistenceContext
    private EntityManager em;
    private final Logger log = LoggerFactory.getLogger(CompanyService.class);
    private final JobRepository jobRepository;
    private final ValidatorUtil validatorUtil;

    public JobService(JobRepository jobRepository,
                      ValidatorUtil validatorUtil) {
        this.jobRepository = jobRepository;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public JobDTO addJob(JobDTO jobDTO) {
        return new JobDTO(addJob(jobDTO.getName(), jobDTO.getHourlyRate()));
    }

    @Transactional
    public Job addJob(String jobName, Double jobHourlyRate) {
        Job job = new Job(jobName, jobHourlyRate);
        validatorUtil.validate(job);
        return jobRepository.save(job);
    }

    @Transactional(readOnly = true)
    public Job findJobByName(String jobName) {
        List<Job> jobs = jobRepository.findAll();
        for (Job job : jobs) {
            if (job.getName().equals(jobName))
                return job;
        }
        throw new EntityNotFoundException(String.format("Job with name [%s] is not found", jobName));
    }

    @Transactional(readOnly = true)
    public Job findJob(Long id) {
        final Optional<Job> job = jobRepository.findById(id);
        return job.orElseThrow(() -> new JobNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Job> findAllJobs() {
        return jobRepository.findAll();
    }

    public JobDTO updateJob(JobDTO jobDTO) {
        return new JobDTO(updateJob(jobDTO.getId(), jobDTO.getName(),
                jobDTO.getHourlyRate()));
    }

    @Transactional
    public Job updateJob(long jobID, String jobName, Double jobHourlyRate) {
        if (!StringUtils.hasText(jobName)) {
            throw new IllegalArgumentException("Job name is null or empty");
        }
        final Job job = findJob(jobID);
        job.setName(jobName);
        job.setHourlyRate(jobHourlyRate);
        validatorUtil.validate(job);
        return jobRepository.save(job);
    }

    @Transactional
    public Job deleteJob(Long id) {
        final Job job = findJob(id);
        jobRepository.delete(job);
        return job;
    }
}
