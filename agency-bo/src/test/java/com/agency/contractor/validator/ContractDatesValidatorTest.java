package com.agency.contractor.validator;

import com.agency.contractmanagement.validator.ContractDatesValidator;
import com.agency.dto.contractwork.BasicContractDetailsDto;
import com.agency.dto.contractwork.ContractWorkCreateDto;
import com.agency.exception.AgencyException;
import com.agency.project.model.Project;
import com.agency.project.model.ProjectBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContractDatesValidatorTest {

    Project project = getProject();

    private final static LocalDate PROJECT_SIGN_DATE = LocalDate.of(2024, 6, 1);
    private final static LocalDate PROJECT_START_DATE = LocalDate.of(2024, 6, 1);
    private final static LocalDate PROJECT_END_DATE = LocalDate.of(2024, 6, 30);

    @ParameterizedTest
    @MethodSource("checkCorrectDate")
    public void should_valid_when_dates_are_on_project_range(LocalDate signDate, LocalDate startDate, LocalDate endDate){
        // given
        ContractWorkCreateDto contract = getContractWork(signDate, startDate, endDate);

        // then
        assertDoesNotThrow(() -> ContractDatesValidator.isContractHasCorrectDatesForProject(project, contract));
    }

    @Test
    public void should_invalid_when_startDate_is_after_endDate(){
        // given
        ContractWorkCreateDto contract = getContractWork(LocalDate.of(2024, 6, 2),
                LocalDate.of(2024, 6, 5), LocalDate.of(2024, 6, 4));

        // then
        assertThrows(AgencyException.class, () -> ContractDatesValidator.isContractHasCorrectDatesForProject(project, contract));
    }

    @Test
    public void should_invalid_when_signDate_are_after_project_startDate(){
        // given
        ContractWorkCreateDto contract = getContractWork(LocalDate.of(2024, 6, 2),
                LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30));

        // then
        assertThrows(AgencyException.class, () -> ContractDatesValidator.isContractHasCorrectDatesForProject(project, contract));
    }

    @ParameterizedTest
    @MethodSource("checkInvalidStartDates")
    public void should_invalid_when_startDate_is_not_on_project_range(LocalDate startDate){
        // given
        ContractWorkCreateDto contract = getContractWork(LocalDate.of(2024, 5, 31),
                startDate, LocalDate.of(2024, 6, 30));

        // then
        assertThrows(AgencyException.class, () -> ContractDatesValidator.isContractHasCorrectDatesForProject(project, contract));
    }

    @ParameterizedTest
    @MethodSource("checkInvalidEndDates")
    public void should_invalid_when_endDate_is_not_on_project_range(LocalDate endDate){
        // given
        ContractWorkCreateDto contract = getContractWork(LocalDate.of(2024, 5, 31),
                LocalDate.of(2024, 6, 1), endDate);

        // then
        assertThrows(AgencyException.class, () -> ContractDatesValidator.isContractHasCorrectDatesForProject(project, contract));
    }


    private static Stream<Arguments> checkInvalidStartDates(){
        return Stream.of(
                Arguments.of(LocalDate.of(2024, 5, 3)),
                Arguments.of(LocalDate.of(2024, 7, 1))
        );
    }

    private static Stream<Arguments> checkInvalidEndDates(){
        return Stream.of(
                Arguments.of(LocalDate.of(2024, 5, 31)),
                Arguments.of(LocalDate.of(2024, 7, 1))
        );
    }

    private static Stream<Arguments> checkCorrectDate(){
        return Stream.of(
                Arguments.of(LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30)),
                Arguments.of(LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 1)),
                Arguments.of(LocalDate.of(2024, 6, 30), LocalDate.of(2024, 6, 30), LocalDate.of(2024, 6, 30)),
                Arguments.of(LocalDate.of(2024, 6, 10), LocalDate.of(2024, 6, 11), LocalDate.of(2024, 6, 20))
        );
    }

    private ContractWorkCreateDto getContractWork(LocalDate signDate, LocalDate startDate, LocalDate endDate) {
        return ContractWorkCreateDto.builder()
                .contractDetailsDto(BasicContractDetailsDto.builder()
                        .signDate(signDate)
                        .startDate(startDate)
                        .endDate(endDate)
                        .build())
                .build();
    }

    private Project getProject() {
        return ProjectBuilder.aProjectBuilder()
                .withSignDate(PROJECT_SIGN_DATE)
                .withStartDate(PROJECT_START_DATE)
                .withEndDate(PROJECT_END_DATE)
                .buildProject();
    }

}
