package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.DocumentTypeBean;
import in.cdac.university.globalService.repository.DocumentRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentTypeService {

    @Autowired
    private DocumentRepository documentRepository;

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
}
