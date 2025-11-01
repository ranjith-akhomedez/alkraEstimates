package com.alkra.estimates.controller;

import com.alkra.estimates.dto.EstimateRequest;
import com.alkra.estimates.dto.EstimateResponse;
import com.alkra.estimates.service.EstimateService;
import com.alkra.estimates.service.PdfService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estimates")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EstimateController {

    private final EstimateService estimateService;
    private final PdfService pdfService;

    @PostMapping
    public ResponseEntity<EstimateResponse> createEstimate(@Valid @RequestBody EstimateRequest request) {
        EstimateResponse response = estimateService.createEstimate(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstimateResponse> getEstimate(@PathVariable Long id) {
        EstimateResponse response = estimateService.getEstimate(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<EstimateResponse>> getAllEstimates() {
        List<EstimateResponse> estimates = estimateService.getAllEstimates();
        return ResponseEntity.ok(estimates);
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadEstimatePdf(@PathVariable Long id) {
        EstimateResponse estimate = estimateService.getEstimate(id);
        byte[] pdfBytes = pdfService.generateEstimatePdf(estimate);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "estimate-" + id + ".pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
