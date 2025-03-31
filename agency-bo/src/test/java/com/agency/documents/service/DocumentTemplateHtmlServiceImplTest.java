package com.agency.documents.service;

import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.documentcontext.templatecontext.TemplateContext;
import com.agency.documents.model.TemplateDocument;
import com.agency.documents.model.TemplateDocumentBuilder;
import com.agency.documents.repository.TemplateDocumentRepository;
import com.agency.dto.document.TemplateDocumentCreateRequest;
import com.agency.dto.document.TemplateDocumentDto;
import com.agency.exception.AgencyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DocumentTemplateHtmlServiceImplTest {

    private DocumentTemplateHtmlServiceImpl documentTemplateHtmlService;

    @Mock
    private TemplateDocumentRepository documentRepository;
    @Mock
    private DefaultDocumentTemplateResolver resolver;

    @BeforeEach
    void setUp() {
        documentTemplateHtmlService = new DocumentTemplateHtmlServiceImpl(documentRepository, resolver);
    }

    @Test
    void should_save_template_with_html_content() {
        // given
        TemplateDocumentCreateRequest request = getTemplateDocumentCreateDto();
        TemplateDocument templateDocument = TemplateDocumentBuilder.aTemplateDocument()
                .withTemplateName(request.templateName())
                .withDefault(request.isDefault())
                .withTemplateContext(request.templateContext())
                .withHtmlContent(request.htmlContent())
                .build();

        given(documentRepository.existsTemplateDocumentByTemplateName(request.templateName())).willReturn(false);
        given(documentRepository.save(any(TemplateDocument.class))).willReturn(templateDocument);

        // when
        TemplateDocumentDto templateDocumentDto = documentTemplateHtmlService.saveDocumentTemplate(request);

        // then
        assertNotNull(templateDocumentDto);
        assertTrue(templateDocumentDto.isDefault());
        assertEquals(TemplateContext.CONTRACT_WORK, templateDocument.getTemplateContext());
        assertNull(templateDocumentDto.filename());
        assertNull(templateDocumentDto.referenceId());
    }

    @Test
    public void should_not_save_when_template_name_is_not_unique() {
        // given
        TemplateDocumentCreateRequest request = getTemplateDocumentCreateDto();
        given(documentRepository.existsTemplateDocumentByTemplateName(request.templateName())).willReturn(true);

        // then
        assertThrows(AgencyException.class, () -> documentTemplateHtmlService.saveDocumentTemplate(request));

    }

    private TemplateDocumentCreateRequest getTemplateDocumentCreateDto() {
        String htmlContent = "<p class=\"ql-align-center\"><strong>UMOWA O DZIEŁO SPERSONALIZOWANA</strong></p><p class=\"ql-align-center\">{{ContractWork_contractNumber}}</p><p class=\"ql-align-center\"><br></p><p class=\"ql-align-justify\">zawarta w dniu {{ContractWork_signDate}} pomiędzy:</p><ol><li data-list=\"ordered\" class=\"ql-align-justify\"><span class=\"ql-ui\" contenteditable=\"false\"></span>Panem {{AgencyDetails_firstName}} {{AgencyDetails_lastName}}, nr PESEL: {{AgencyDetails_pesel}}, NIP: {{AgencyDetails_nip}} prowadzącym jednoosobową działalność gospodarczą pod nazwą {{AgencyDetails_agencyName}} z siedzibą w {{AgencyDetails_address_zipCode}} {{AgencyDetails_address_city}}, ul. {{AgencyDetails_address_street}} {{AgencyDetails_address_houseNumber}}/{{AgencyDetails_address_apartmentNumber}} – zwany dalej „ZAMAWIAJĄCYM”</li></ol><p class=\"ql-align-justify\">a</p><ol><li data-list=\"ordered\" class=\"ql-align-justify\"><span class=\"ql-ui\" contenteditable=\"false\"></span>Panem {{Contractor_firstName}} {{Contractor_lastName}}, nr PESEL: {{Contractor_pesel}}, zamieszkałym w {{Contractor_address_zipCode}} {{Contractor_address_city}}, ul. {{Contractor_address_street}}  {{Contractor_address_houseNumber}}/{{Contractor_address_apartmentNumber}}, zwanym dalej „WYKONAWCĄ\"</li></ol><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">Strony zawierają umowę o następującej treści:</p><p class=\"ql-align-center\">§ 1</p><p class=\"ql-align-center\">1. Zamawiający zamawia wykonanie, a Wykonawca zobowiązuje się wykonać dzieło polegające na<strong> {{ContractWork_contractSubject}}.</strong></p><p class=\"ql-align-center\">2.Zespół Rumba Wachaca podczas koncertu wykona swoje interpretacje popularnych utworów latyno-amerykańskich.</p><p class=\"ql-align-center\">§ 2</p><p class=\"ql-align-center\">Wykonanie dzieła nastąpi dniu {{ContractWork_startDate}}.</p><p class=\"ql-align-center\">§ 3</p><p class=\"ql-align-center\">1. Za wykonane działo Wykonawca otrzyma wynagrodzenie brutto w wysokości <strong>{{ContractWork_salary}} zł</strong>, z którego zostanie naliczony podatek dochodowy od osób fizycznych zgodnie z obowiązującymi przepisami.</p><p class=\"ql-align-center\">2. Ustalona kwota zostanie wypłacona Wykonawcy przelewem na wskazane konto w ciągu 7 dni od złożenia rachunku przez Wykonawcę. </p><p class=\"ql-align-center\">§ 4</p><p class=\"ql-align-center\">1. Wykonawca oświadcza, że przedstawione w § 1 umowy partie wokalne i perkusyjne oraz występ artystyczny są przedmiotem prawa autorskiego w myśl przepisów ustawy z dnia 4 lutego 1994 o prawie autorskim i prawach pokrewnych (j. t. Dz. U 2016 r., poz. 666) i posiada pełnię praw autorskich do wykonywanego dzieła.</p><p class=\"ql-align-center\">2. Wykonawca zapewnia, że przy wykonaniu niniejszej umowy nie narusza praw autorskich osób trzecich.</p><p class=\"ql-align-center\">§ 5</p><p class=\"ql-align-center\">Wykonawca oświadcza, że posiada stosowne umiejętności potrzebne do wykonania niniejszej umowy.</p><p class=\"ql-align-center\">§ 6</p><p class=\"ql-align-center\">1. Z dniem wykonania dzieła na Zamawiającego przechodzą autorskie prawa majątkowe i prawa pokrewne do wyłącznego, nieograniczonego w czasie i przestrzeni rozporządzania i korzystania z działa, w tym również prawo do wykonywania praw zależnych.</p><p class=\"ql-align-center\">2. Wynagrodzenie ujęte w §2 niniejszej umowy obejmuje wynagrodzenie za przeniesienie autorskich praw majątkowych oraz praw pokrewnych do dzieła<strong>.</strong></p><p class=\"ql-align-center\">§ 7</p><p class=\"ql-align-center\">1. Zmiany w umowie wymagają formy pisemnej pod rygorem nieważności.</p><p class=\"ql-align-center\">2. W sprawach nie uregulowanych umową mają zastosowanie przepisy kodeksu cywilnego.</p><p class=\"ql-align-center\">3. Umowa została sporządzona w dwóch jednobrzmiących egzemplarzach, po jednym dla każdej ze stron.</p><p class=\"ql-align-center\"><br></p><p class=\"ql-align-center\"> </p><p> ……………………. …………………….</p><p> (Zamawiający) (Wykonawca)</p>";
        return new TemplateDocumentCreateRequest(
                htmlContent,
                "Template Name",
                DocContextType.TEMPLATE,
                true,
                TemplateContext.CONTRACT_WORK
        );
    }
}
