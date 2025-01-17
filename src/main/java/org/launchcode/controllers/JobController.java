package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {


    private JobData jobData = JobData.getInstance();
    private JobForm jobForm;

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {
        model.addAttribute("job", jobData.findById(id));
        // TODO #1 - get the Job with the given ID and pass it into the view

        return "job-detail";
    }


    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid JobForm jobForm, Errors errors) {
        if (errors.hasErrors()){
            return "new-job";
        }

        Employer anEmployer = jobData.getEmployers().findById(jobForm.getEmployerId());
        Location aLocation = jobData.getLocations().findById(jobForm.getLocationId());
        PositionType aPositionType = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());
        CoreCompetency aSkill = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetenciesId());

        Job newJob = new Job(jobForm.getName(), anEmployer, aLocation, aPositionType, aSkill);
        jobData.add(newJob);

        model.addAttribute("job", newJob);

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        return "job-detail";
    }

}

