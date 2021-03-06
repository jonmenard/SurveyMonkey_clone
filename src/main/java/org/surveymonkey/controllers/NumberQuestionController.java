package org.surveymonkey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.surveymonkey.models.NumberQuestion;
import org.surveymonkey.models.Question;
import org.surveymonkey.models.Survey;
import org.surveymonkey.services.iservices.IQuestionService;
import org.surveymonkey.services.iservices.ISurveyService;

import java.util.List;

@Controller
public class NumberQuestionController extends ApplicationController  {

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private ISurveyService surveyService;



    private final String TOPIC = "Question";

    @GetMapping(value = "/survey/{surveyID}/numberquestion/{questionID}/bounds")
    public String getChangeBoundsTemplate(@PathVariable long surveyID, @PathVariable int questionID, Model model) {
        Survey survey = surveyService.findById(surveyID);
        model.addAttribute("survey", survey);

        Question question = survey.findQuestion(questionID);
        if(question instanceof NumberQuestion){
            model.addAttribute("question", (NumberQuestion) question);

            return "changeQuestionBounds";
        }else{
            return "redirect:/survey/" + surveyID + "/" + survey.getEndUserId();
        }
    }

    @PostMapping(value = "/survey/{surveyID}/numberquestion/{questionID}/bounds")
    public String postChangeBoundsTemplate(@PathVariable long surveyID, @PathVariable int questionID, @RequestParam int lowerBound, @RequestParam int upperBound) {

        Survey survey = surveyService.findById(surveyID);
        NumberQuestion question = (NumberQuestion) survey.findQuestion(questionID);

        if (lowerBound <= upperBound) {
            question.setLowerBound(lowerBound);
            question.setUpperBound(upperBound);
        } else {
            question.setLowerBound(upperBound);
            question.setUpperBound(lowerBound);
        }
        surveyService.save(survey);

        return  "redirect:/survey/" + surveyID + "/" + survey.getEndUserId();
    }

    @PostMapping(value = "/survey/{surveyID}/numberquestion", produces = MediaType.APPLICATION_JSON_VALUE)
    public String postNumberQuestion(@PathVariable long surveyID, @RequestParam String question) {
        Survey survey = surveyService.findById(surveyID);
        NumberQuestion numberQuestion = new NumberQuestion(question);
        survey.addQuestion(numberQuestion);
        surveyService.save(survey);
        List<Question> questions = survey.getQuestions();
        numberQuestion = (NumberQuestion) questions.get(questions.size() - 1);



        return "redirect:/survey/" + surveyID + "/numberquestion/" + numberQuestion.getId() + "/bounds";
    }

    @DeleteMapping(value = "/survey/{surveyID}/numberquestion/{numberQuestionID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Survey deleteNumberQuestion(@PathVariable long surveyID, @PathVariable long numberQuestionID) {
        Survey survey = surveyService.findById(surveyID);
        Question numberQuestion = questionService.findById(numberQuestionID);
        survey.removeQuestion(numberQuestion);
        surveyService.save(survey);

        return survey;
    }

    @GetMapping(value = "/NumberQuestionController/test")
    @ResponseBody
    public String testNumberQuestionController() {
        return "NumberQuestionController is working";
    }


}