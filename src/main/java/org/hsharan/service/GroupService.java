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
    public Boolean isUserInGroup(GroupEntity group,UserEntity user) throws ServiceException {
        if(group==null){
            throw new ServiceException("Unable to find Group");
        }
        if(user==null){
            throw new ServiceException("Unable to find User");
        }
        boolean isPresent =false;
        for (UserEntity var : group.getUsers()){
            if(var.getId().equals(user.getId())){
                isPresent =true;
                break;
            }
        }
        return isPresent;
/*        if(!group.getUsers().contains(user)){
            *//*throw new ServiceException(
                    MessageFormat.format(
                            "Unable to find user By Id : {0} in Group by Id: {1}",
                            user.getId(),
                            group.getGroupId()
                    ));*//*
            return false;
        }
        else{
            return true;
        }*/
    }

    public Boolean isUserGroupCreator(GroupEntity group,UserEntity user) throws ServiceException {
        if(!group.getCreatedBy().equals(user)){
            /*throw new ServiceException(
                    MessageFormat.format(
                            "User Id : {0} is not the creator to group id {1}",
                            user.getId(),group.getGroupId()
                    ));*/
            return false;
        }
        else{
            return true;
        }
    }
    public void addMember(String groupId,String userId,UserEntity addedBy) throws ServiceException {
        UserEntity user = userService.getUserById(userId);

        GroupEntity group = getGroupById(groupId);


        if(group == null){
            throw new ServiceException(MessageFormat.format("Unable to find Group By Id : {0}",groupId));
        }

        if(user == null){
            throw new ServiceException(MessageFormat.format("Unable to find user By Id : {0}",userId));
        }

        if(!isUserInGroup(group,addedBy)){
            throw new ServiceException("You don't have sufficient privilege to add member to this group");
        }
        if(isUserInGroup(group,user)){
            throw new ServiceException(
                    MessageFormat.format(
                            "Given user with id : {0} is already memeber of the group",
                            user.getId()
                    ));
        }
        group.getUsers().add(user);
        user.getGroupList().add(group);

        groupRepository.save(group);
        userService.addUser(user);
    }

    public GroupEntity getGroupById(String groupId){
        return groupRepository.findById(groupId).orElse(null);
    }
}
