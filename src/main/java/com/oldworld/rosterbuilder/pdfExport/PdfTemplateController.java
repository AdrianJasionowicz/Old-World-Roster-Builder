package com.oldworld.rosterbuilder.pdfExport;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class PdfTemplateController {

    private final PdfTemplateService pdfTemplateService;

    @GetMapping("/armies/{armyId}/pdf")
    ResponseEntity<byte[]> generateArmyPdf(@PathVariable Long armyId) {
        var pdfBytes = pdfTemplateService.generateArmyPdf(armyId);
        var contentDisposition = ContentDisposition.inline()
                .filename("army-" + armyId + ".pdf")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
