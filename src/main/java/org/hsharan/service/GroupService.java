package org.hsharan.service;

import org.hsharan.dto.GroupDto;
import org.hsharan.entity.UserEntity;
import org.hsharan.entity.GroupEntity;
import org.hsharan.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    @Autowired
    private UserService userService;
    @Autowired
    private GroupRepository groupRepository;
    public GroupEntity convertToEntity(GroupDto groupDto, UserEntity createdBy) throws ServiceException {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setCreatedAt(new Date());
        groupEntity.setName(groupDto.getName());
        groupEntity.setCreatedBy(createdBy);
        List<UserEntity> usersList = new ArrayList<>();
        usersList.add(createdBy);
        for(String id : groupDto.getUsersId()){
            UserEntity user = userService.getUserById(id);
            if(user!=null){
                usersList.add(user);
            }
            else{
                throw new ServiceException(MessageFormat.format("Unable to Find User Id : {0}",id));
            }
        }
        usersList = usersList.stream().distinct().collect(Collectors.toList());
        groupEntity.setUsers(usersList);
        return groupEntity;
    }
    public void addGroup(GroupEntity group){

        groupRepository.save(group);
        for(UserEntity user: group.getUsers()){
            if(user.getGroupList()==null)
                user.setGroupList(new ArrayList<>());
            user.getGroupList().add(group);
            userService.addUser(user);
        }

    }

    public void removeMemberFromGroup(String userId,String groupId,UserEntity reqUser) throws ServiceException{
        GroupEntity group = groupRepository.findById(groupId).
                orElseThrow(
                        ()->new SecurityException(MessageFormat.format("Unable to find Group Id : {0}",groupId)
                        ));
        if(!group.getCreatedBy().getId().equals(reqUser.getId())){
            throw new ServiceException("You don't have sufficient privilege to perform this operation");
        }
        UserEntity memberToRemove = null;
        for(UserEntity user: group.getUsers()){
            if(user.getId().equals(userId)){
                memberToRemove = user;
                user.getGroupList().remove(group);
                userService.addUser(user);
                break;
            }
        }


        if(memberToRemove==null){
            throw new ServiceException(MessageFormat.format("No Member with Id {0} found ",userId));
        }
        group.getUsers().remove(memberToRemove);
        groupRepository.save(group);
    }
}
