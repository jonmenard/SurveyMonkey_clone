package org.surveymonkey.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.surveymonkey.models.ChoiceQuestion;
import org.surveymonkey.models.NumberQuestion;
import org.surveymonkey.models.Question;
import org.surveymonkey.models.Survey;
import org.surveymonkey.repositories.QuestionRepository;
import org.surveymonkey.services.iservices.IQuestionService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class QuestionService implements IQuestionService {

    @Autowired
    QuestionRepository questionRepository;



    @Override
    public Question findById(long id) {
        return questionRepository.findById(id);
    }

    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }


}