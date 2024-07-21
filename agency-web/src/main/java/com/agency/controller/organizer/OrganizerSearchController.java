package com.agency.controller.organizer;

import com.agency.dto.organizer.OrganizerDto;
import com.agency.dto.organizer.OrganizerSearchResultDto;
import com.agency.service.OrganizerSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/organizer")
@PreAuthorize("hasAnyAuthority('ORGANIZER_MANAGEMENT', 'ORGANIZER_VIEW')")
@RequiredArgsConstructor
public class OrganizerSearchController {

    private final OrganizerSearchService service;

    @GetMapping("/{public-id}")
    @Transactional(readOnly = true)
    public ResponseEntity<OrganizerDto> getOrganizerById(@PathVariable("public-id") String publicId) {
        return ResponseEntity.ok(service.findByPublicId(publicId));
    }


    @GetMapping
    public ResponseEntity<Page<OrganizerSearchResultDto>> getAllOrganizers(@RequestParam("page") int page,
                                                                           @RequestParam("size") int size,
                                                                           @RequestParam(required = false, value = "sort") String sort,
                                                                           @RequestParam(required = false, value = "order") String order) {
        return ResponseEntity.ok(service.findAll(page, size, sort, order));
    }

    @GetMapping("/my-organizers")
    public ResponseEntity<List<OrganizerDto>> getMyOrganizers() {
        return ResponseEntity.ok(service.findByUsername());
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<OrganizerSearchResultDto>> getOrganizersByName(@RequestParam("page") int page,
                                                                              @RequestParam("size") int size,
                                                                              @RequestParam(required = false, value = "sort") String sort,
                                                                              @RequestParam(required = false, value = "order") String order,
                                                                              @RequestParam("organizerName") String organizerName){
        return ResponseEntity.ok(service.findAllByOrganizerName(page, size, sort, order, organizerName));
    }
}
