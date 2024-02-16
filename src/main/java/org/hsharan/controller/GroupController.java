package org.hsharan.controller;

import org.hsharan.dto.GroupDto;
import org.hsharan.service.GroupService;
import org.hsharan.service.ServiceException;
import org.hsharan.service.UserService;
import org.hsharan.userAuth.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupService groupService;
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<String> addGroups(@RequestBody GroupDto group){

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            groupService.addGroup(groupService.convertToEntity(group,userService.convertAuthUserToUserEntity(user)));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok("Group Added Successfully");
    }
    @DeleteMapping("/{groupId}/removeMember/{userId}")
    public ResponseEntity<String> removeMember(@PathVariable String userId,@PathVariable String groupId){
        UserDetails user= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try{
            groupService.removeMemberFromGroup(userId,groupId,userService.convertAuthUserToUserEntity(user));
        }
        catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok("Successfully Removed the Member");
    }

    @PostMapping("/{groupId}/addMember/{userId}")
    public ResponseEntity<String> addMember(@PathVariable String groupId,@PathVariable String userId){
        UserDetails user= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            groupService.addMember(groupId,userId,userService.convertAuthUserToUserEntity(user));
        }
        catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok("Member added Successfully");
    }


}
