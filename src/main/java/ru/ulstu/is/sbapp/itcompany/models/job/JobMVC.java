package ru.ulstu.is.sbapp.itcompany.models.job;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.itcompany.services.JobService;

import javax.validation.Valid;

@Controller
@RequestMapping("/job")
public class JobMVC {
    private final JobService jobService;

    public JobMVC(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public String getJobs(Model model) {
        model.addAttribute("jobs",
                jobService.findAllJobs().stream()
                        .map(JobDTO::new)
                        .toList());
        return "job";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editJob(@PathVariable(required = false) Long id, Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("jobDto", new JobDTO());
        }
        else {
            model.addAttribute("jobId", id);
            model.addAttribute("jobDto", new JobDTO(jobService.findJob(id)));
            model.addAttribute("developers", jobService.findJob(id).getDevelopers());
        }
        return "job-edit";
    }

    @PostMapping(value = {"", "/{id}"})
    public String saveJob(@PathVariable(required = false) Long id,
                          @ModelAttribute @Valid JobDTO jobDto,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "job-edit";
        }
        if (id == null || id <= 0) {
            jobService.addJob(jobDto.getName(), jobDto.getHourlyRate());
        } else {
            jobService.updateJob(id, jobDto.getName(), jobDto.getHourlyRate());
        }
        return "redirect:/job";
    }

    @PostMapping("/delete/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return "redirect:/job";
    }
}
