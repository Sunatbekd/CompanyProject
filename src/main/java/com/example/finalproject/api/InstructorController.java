package com.example.finalproject.api;

import com.example.finalproject.dto.InstructorRequest;
import com.example.finalproject.dto.InstructorResponse;
import com.example.finalproject.dto.InstructorResponseView;
import com.example.finalproject.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/instructor")
@PreAuthorize("hasAuthority('ADMIN')")
public class InstructorController {

    private final InstructorService instructorService;
    @PostMapping
    public InstructorResponse saveInstructor(@RequestBody InstructorRequest request){
        return instructorService.saveInstructor(request);
    }
    @GetMapping("{id}")
    public InstructorResponse getInstructorById(@PathVariable Long id){
        return instructorService.getInstructorById(id);
    }

    @DeleteMapping("{id}")
    public InstructorResponse deleteInstructorById(@PathVariable Long id){
        return instructorService.deleteInstructorById(id);
    }

    @PutMapping("{id}")
    public InstructorResponse updateInstructorById(@PathVariable Long id,
                                                   @RequestBody InstructorRequest request){
        return instructorService.updateInstructorById(id,request);
    }

//    @GetMapping("/all")
//    public List<InstructorResponse> getAll(){
//        return instructorService.getAll();
//    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public InstructorResponseView getPagination(@RequestParam(name = "text",required = false) String text,
                                                @RequestParam int page,
                                                @RequestParam int size){
        return instructorService.getInstructorPagination(text,page,size);
    }
}
