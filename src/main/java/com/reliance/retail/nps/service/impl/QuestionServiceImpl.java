package com.reliance.retail.nps.service.impl;

import com.reliance.retail.nps.domain.Answer;
import com.reliance.retail.nps.domain.Question;
import com.reliance.retail.nps.domain.enumeration.QuestionType;
import com.reliance.retail.nps.repository.AnswerRepository;
import com.reliance.retail.nps.repository.QuestionRepository;
import com.reliance.retail.nps.service.QuestionService;
import com.reliance.retail.nps.service.dto.QuestionDTO;
import com.reliance.retail.nps.service.mapper.QuestionMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Question}.
 */
@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private final QuestionRepository questionRepository;

    private final QuestionMapper questionMapper;
    private final AnswerRepository answerRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionMapper questionMapper, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.answerRepository = answerRepository;
    }

    @Override
    public QuestionDTO save(QuestionDTO questionDTO) {
        log.debug("Request to save Question : {}", questionDTO);
        Question question = questionMapper.toEntity(questionDTO);
        question = questionRepository.save(question);
        return questionMapper.toDto(question);
    }

    @Override
    public QuestionDTO update(QuestionDTO questionDTO) {
        log.debug("Request to save Question : {}", questionDTO);
        Question question = questionMapper.toEntity(questionDTO);
        question = questionRepository.save(question);
        return questionMapper.toDto(question);
    }

    @Override
    public Optional<QuestionDTO> partialUpdate(QuestionDTO questionDTO) {
        log.debug("Request to partially update Question : {}", questionDTO);

        return questionRepository
            .findById(questionDTO.getId())
            .map(existingQuestion -> {
                questionMapper.partialUpdate(existingQuestion, questionDTO);

                return existingQuestion;
            })
            .map(questionRepository::save)
            .map(questionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuestionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Questions");
        return questionRepository.findAll(pageable).map(questionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionDTO> findOne(Long id) {
        log.debug("Request to get Question : {}", id);
        return questionRepository.findById(id).map(questionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Question : {}", id);
        questionRepository.deleteById(id);
    }

    @Override
    public Optional<List<QuestionDTO>> findQuestionByCampaignId(Long id) {
        return questionRepository.findByCampaignId(id).map(questionMapper::toDto)
            .map(questionDTOS -> {
                for(QuestionDTO question: questionDTOS) {
                    if(question.getAnswers().isEmpty() && (question.getType() == QuestionType.MultiSelect || question.getType() == QuestionType.SingleSelect)) {
                        Set<Answer> answers = answerRepository.findByQuestionId(question.getId()).get();
                        question.setAnswers(answers);
                    }
                }
                return questionDTOS;
            });
    }
}
