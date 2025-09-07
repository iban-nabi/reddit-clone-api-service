package com.parallelquantumcorp.redditcloneapiservice.service;

import com.parallelquantumcorp.redditcloneapiservice.dtos.SubRedditDto;
import com.parallelquantumcorp.redditcloneapiservice.dtos.UpdateSubRedditRequest;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.SubRedditMembersRepository;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.SubRedditRepository;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.UserRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.SubReddit;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import com.parallelquantumcorp.redditcloneapiservice.mappers.SubRedditMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubRedditService {
    //repositories
    private final SubRedditRepository subRedditRepository;
    private final SubRedditMembersRepository subRedditMembersRepository;

    //mappers
    private final SubRedditMapper subRedditMapper;
    private final UserRepository userRepository;

    public List<SubRedditDto> getAllSubReddits(){
        return subRedditRepository.getSubReddits()
                .stream()
                .map(subRedditMapper::toDto)
                .toList();
    }

    public List<SubRedditDto> searchSubReddits(String query){
        return subRedditRepository.searchSubReddit(query)
                .stream()
                .map(subRedditMapper::toDto)
                .toList();
    }

    public boolean createSubReddit(SubRedditDto subRedditDto){
        if(subRedditRepository.existsByName(subRedditDto.getName())){
            return false;
        }

        User user = userRepository.findByUsername(subRedditDto.getCreator().getUsername());

        if(user==null){
            return false;
        }

        SubReddit subReddit = SubReddit.builder()
                .name(subRedditDto.getName())
                .description(subRedditDto.getDescription())
                .archived(false)
                .build();

        subRedditRepository.save(subReddit);
        subRedditMembersRepository.createSubRedditMembers(subReddit);
        subRedditMembersRepository.addMember(subReddit.getName(), user);
        return true;
    }

    public boolean updateSubReddit(String name, UpdateSubRedditRequest updateSubRedditRequest){
        if(subRedditRepository.existsByName(name)){
            subRedditRepository.update(name, updateSubRedditRequest);
            return true;
        }
        return false;
    }

    public boolean deleteSubReddit(String name){
        if(subRedditRepository.existsByName(name)){
            subRedditRepository.delete(name);
            return true;
        }
        return false;
    }
}
