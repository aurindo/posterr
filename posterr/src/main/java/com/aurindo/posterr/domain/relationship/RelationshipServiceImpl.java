package com.aurindo.posterr.domain.relationship;

import com.aurindo.posterr.domain.model.User;
import com.aurindo.posterr.infrastructure.repository.RelationshipRepository;
import com.aurindo.posterr.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class RelationshipServiceImpl implements RelationshipService {

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Long numberOfFollowersByUser(String userId) {
        User user =userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(userId));

        return relationshipRepository.numberOfFollowersByUser(user);
    }

    @Override
    public Long numberOffollowedsByUser(String userId) {
        User user =userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(userId));

        return relationshipRepository.numberOffollowedsByUser(user);
    }
}
