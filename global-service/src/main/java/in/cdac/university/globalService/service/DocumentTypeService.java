package in.cdac.university.globalService.service;


import in.cdac.university.globalService.bean.DocumentTypeBean;
import in.cdac.university.globalService.entity.GmstDocumentMst;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.DocumentRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentTypeService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private Language language;

    public ServiceResponse getDocumentTypeListForNotification() {
        List<DocumentTypeBean> documentTypeList = documentRepository.getAllDocuments().stream()
                .map(gmstDocumentMst -> {
                    DocumentTypeBean documentTypeBean = BeanUtils.copyProperties(gmstDocumentMst, DocumentTypeBean.class);
                    if (documentTypeBean.getUnumDocId() == 1 || documentTypeBean.getUnumDocId() == 2)
                        documentTypeBean.setIsMandatory(1);
                    return documentTypeBean;
                })
                .toList();

        return ServiceResponse.builder()
                .status(1)
                .responseObject(documentTypeList)
                .build();
    }

    public List<DocumentTypeBean> listPageData(Integer status) throws Exception {
        return BeanUtils.copyListProperties(
                documentRepository.listPageData(status),
                DocumentTypeBean.class
        );
    }

    public ServiceResponse getDocumentType(Long docId) {
        Optional<GmstDocumentMst> docMstOptional = documentRepository.findByUnumIsvalidInAndUnumDocId(
                List.of(1, 2), docId
        );

        if (docMstOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Document", docId));
        }

        return ServiceResponse.builder()
                .status(1)
                .responseObject(BeanUtils.copyProperties(docMstOptional.get(), DocumentTypeBean.class))
                .build();
    }

    @Transactional
    public ServiceResponse save(DocumentTypeBean documentTypeBean) {
        //for duplicate
        List<GmstDocumentMst> documentTypeList = documentRepository.findByUnumIsvalidInAndUstrDocNameIgnoreCase(
                List.of(1, 2), documentTypeBean.getUstrDocName()
        );
        if (!documentTypeList.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Document", documentTypeBean.getUstrDocName()));
        }

        GmstDocumentMst gmstDocumentMst = BeanUtils.copyProperties(documentTypeBean, GmstDocumentMst.class);
        Integer docId = documentRepository.getNextId();
        gmstDocumentMst.setUnumDocId(Long.valueOf(docId));
        documentRepository.save(gmstDocumentMst);

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("Document"))
                .build();
    }

    //for update
    @Transactional
    public ServiceResponse update(DocumentTypeBean documentTypeBean) throws Exception {
        if (documentTypeBean.getUnumDocId() == null) {
            return ServiceResponse.errorResponse((language.mandatory("Document Id")));
        }

        // Duplicate Check
        List<GmstDocumentMst> documentMsts = documentRepository.findByUnumIsvalidInAndUstrDocCodeAndUstrDocNameIgnoreCaseAndUnumDocIdNot(
                List.of(1, 2), documentTypeBean.getUstrDocCode(), documentTypeBean.getUstrDocName(), documentTypeBean.getUnumDocId()
        );

        if (!documentMsts.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Document", documentTypeBean.getUstrDocName()));
        }

        // Create Log
        int noOfRecordsAffected = documentRepository.createLog(List.of((documentTypeBean.getUnumDocId())));
        if (noOfRecordsAffected == 0) {
            throw new ApplicationException(language.notFoundForId("Document Id", documentTypeBean.getUnumDocId()));
        }

        // Save new Data
        GmstDocumentMst documentMst = BeanUtils.copyProperties(documentTypeBean, GmstDocumentMst.class);
        documentMst.setUnumLstModUid(RequestUtility.getUserId());
        documentMst.setUdtLstModDt(new Date());
        documentRepository.save(documentMst);

        return ServiceResponse.builder()
                .status(1)
                .message(language.updateSuccess("Document"))
                .build();
    }

    //for delete
    @Transactional
    public ServiceResponse delete(DocumentTypeBean documentTypeBean, Long[] idsToDelete) {
        if (idsToDelete == null || idsToDelete.length == 0) {
            return ServiceResponse.errorResponse(language.mandatory("Document Id"));
        }

        List<GmstDocumentMst> documentMsts = documentRepository.findByUnumIsvalidInAndUnumDocIdIn(
                List.of(1, 2), List.of(idsToDelete)
        );

        if (documentMsts.size() != idsToDelete.length) {
            return ServiceResponse.errorResponse(language.notFoundForId("Document", Arrays.toString(idsToDelete)));
        }

        // Create Log
        int noOfRowsAffected = documentRepository.createLog(List.of(idsToDelete));
        if (noOfRowsAffected != idsToDelete.length) {
            throw new ApplicationException(language.deleteError("Document"));
        }

        documentMsts.forEach(doc -> {
            doc.setUnumIsvalid(0);
            doc.setUdtEntryDate(documentTypeBean.getUdtEntryDate());
        });

        documentRepository.saveAll(documentMsts);
        return ServiceResponse.builder()
                .status(1)
                .message(language.deleteSuccess("Document"))
                .build();
    }
}
