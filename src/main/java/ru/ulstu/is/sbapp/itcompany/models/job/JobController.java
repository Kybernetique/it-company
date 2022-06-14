package ru.ulstu.is.sbapp.itcompany.models.job;

import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.configuration.WebConfiguration;
import ru.ulstu.is.sbapp.itcompany.services.JobService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/job")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/{id}")
    public JobDTO getJob(@PathVariable Long id) {
        return new JobDTO(jobService.findJob(id));
    }

    @PostMapping
    public JobDTO createJob(@RequestBody @Valid JobDTO jobDTO) {
        return new JobDTO(jobService.addJob(jobDTO.getName(),
                jobDTO.getHourlyRate()));
    }

    @PutMapping("/{id}")
    public JobDTO updateJob(@PathVariable Long id,
                            @RequestBody @Valid JobDTO jobDTO) {
        return new JobDTO(jobService.updateJob(id,
                jobDTO.getName(), jobDTO.getHourlyRate()));
    }

    @GetMapping("/")
    public List<JobDTO> getJobs() {
        return jobService.findAllJobs().stream()
                .map(JobDTO::new)
                .toList();
    }


    @DeleteMapping("/{id}")
    public JobDTO deleteJob(@PathVariable Long id) {
        return new JobDTO(jobService.deleteJob(id));
    }
}
