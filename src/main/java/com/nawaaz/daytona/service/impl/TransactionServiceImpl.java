package com.nawaaz.daytona.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nawaaz.daytona.entity.CVMCondition;
import com.nawaaz.daytona.entity.CVMDetails;
import com.nawaaz.daytona.entity.CVMMethod;
import com.nawaaz.daytona.entity.CVMRuleResult;
import com.nawaaz.daytona.entity.CardDetails;
import com.nawaaz.daytona.entity.CardTransaction;
import com.nawaaz.daytona.entity.IssuerResponse;
import com.nawaaz.daytona.entity.Terminal;
import com.nawaaz.daytona.entity.TransactionType;
import com.nawaaz.daytona.enums.CardScheme;
import com.nawaaz.daytona.enums.CardType;
import com.nawaaz.daytona.exception.InvalidFileFormatException;
import com.nawaaz.daytona.repository.CVMConditionRepository;
import com.nawaaz.daytona.repository.CVMDetailsRepository;
import com.nawaaz.daytona.repository.CVMMethodRepository;
import com.nawaaz.daytona.repository.CVMRuleResultRepository;
import com.nawaaz.daytona.repository.CardDetailsRepository;
import com.nawaaz.daytona.repository.ResponseRepository;
import com.nawaaz.daytona.repository.TerminalRepository;
import com.nawaaz.daytona.repository.TransactionRepository;
import com.nawaaz.daytona.repository.TransactionTypeRepository;
import com.nawaaz.daytona.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    @Autowired
    private CardDetailsRepository cardDetailsRepository;

    @Autowired
    private CVMDetailsRepository cvmDetailsRepository;

    @Autowired
    private CVMMethodRepository cvmMethodRepository;

    @Autowired
    private CVMConditionRepository cvmConditionRepository;

    @Autowired
    private CVMRuleResultRepository cvmRuleResultRepository;

    @Autowired
    private TerminalRepository terminalRepository;

    @Override
    public void processCSV() {
        // TODO externalize
        Path sourceDir = Paths.get("C:\\transactions");
        Path archiveDir = Paths.get("C:\\transactions\\archive");
        Path errorDir = Paths.get("C:\\transactions\\error");

        try (Stream<Path> files = Files.list(sourceDir)) {
            files.filter(Files::isRegularFile)
                    .filter(file -> file.toString().endsWith(".csv"))
                    .forEach(file -> processFile(file, archiveDir, errorDir));
        } catch (IOException e) {
            logger.error("Failed to list files in directory: {}", sourceDir, e);
        }
    }

    @Override
    public List<CardTransaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    private void processFile(Path file, Path archiveDir, Path errorDir) {
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            // TODO refactor to enable error handling for each line
            List<CardTransaction> cardTransactions = reader.lines()
                    // Skipping the header
                    .skip(1)
                    .map(this::parseTransaction)
                    .toList();

            transactionRepository.saveAll(cardTransactions);
            Files.move(file, archiveDir.resolve(file.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            logger.info("File processed and moved to archive: {}", file.getFileName());
        } catch (IOException e) {
            try {
                Files.move(file, errorDir.resolve(file.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                logger.error("Failed to process CSV file: {}. Moved to error directory.", file.getFileName(), e);
            } catch (IOException ex) {
                logger.error("Failed to move file to error directory: {}", file.getFileName(), ex);
            }
        }
    }

    // TODO refactor to create each entity in their own method
    // TODO Check if exists before creating new entities
    private CardTransaction parseTransaction(String line) {
        String[] fields = line.split(",");

        // TODO implement more rigid check
        if (fields.length != 30) {
            throw new InvalidFileFormatException("Invalid CSV format: " + line);
        }

        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setTransactionId(fields[0].trim());
        cardTransaction.setOriginalId(fields[1].trim());
        cardTransaction.setTransactionTimestamp(ZonedDateTime.parse(fields[2].trim()));
        cardTransaction.setReferenceId(fields[3].trim());
        cardTransaction.setAmount(new BigDecimal(fields[4].trim()));

        // Parsing response code and description
        IssuerResponse response = new IssuerResponse();
        response.setResponseCode(Integer.parseInt(fields[5].trim()));
        response.setResponseDescription(fields[6].trim());
        responseRepository.save(response);
        cardTransaction.setIssuerResponse(response);

        cardTransaction.setSystemTraceAuditNumber(Integer.parseInt(fields[7].trim()));

        // Parsing transaction type and description
        TransactionType transactionType = new TransactionType();
        transactionType.setTransactionTypeCode(Integer.parseInt(fields[8].trim()));
        transactionType.setTransactionTypeDescription(fields[9].trim());
        transactionTypeRepository.save(transactionType);
        cardTransaction.setTransactionType(transactionType);

        cardTransaction.setRetrievalReferenceNumber(fields[10].trim());

        // Parsing terminal details
        Terminal terminal = new Terminal();
        terminal.setTerminalId(fields[11].trim());
        terminal.setTerminalSerial(fields[12].trim());
        terminalRepository.save(terminal);
        cardTransaction.setTerminal(terminal);

        // Parsing card details
        CardDetails cardDetails = new CardDetails();
        cardDetails.setPrimaryAccountNumber(fields[13].trim());
        cardDetails.setCardType(StringUtils.isEmpty(fields[14].trim()) ? CardType.UNDEFINED : CardType.valueOf(fields[14].trim().toUpperCase()));
        cardDetails.setCardScheme(StringUtils.isEmpty(fields[15].trim()) ? CardScheme.UNDEFINED : CardScheme.valueOf(fields[15].trim().toUpperCase()));
        cardDetails.setCardIssuer(fields[17].trim());
        cardDetails.setCardIssuerCountry(fields[18].trim());
        cardDetails.setCardHolderName(fields[19].trim());
        cardDetails.setCardExpiryDate(Integer.parseInt(fields[20].trim()));
        cardDetails.setCardBankIdentificationNumber(fields[29].trim());
        cardDetailsRepository.save(cardDetails);
        cardTransaction.setCardDetails(cardDetails);

        // TODO: switch transaction time not mapped
        // fields[16].trim()

        // Parsing CVM details
        CVMDetails cvmDetails = new CVMDetails();

        CVMMethod cvmMethod = new CVMMethod();
        cvmMethod.setCvmMethodCode(Integer.parseInt(fields[21].trim()));
        cvmMethod.setCvmMethodDescription(fields[22].trim());
        cvmMethodRepository.save(cvmMethod);
        cvmDetails.setCvmMethod(cvmMethod);

        CVMCondition cvmCondition = new CVMCondition();
        cvmCondition.setCvmConditionCode(Integer.parseInt(fields[23].trim()));
        cvmCondition.setCvmConditionDescription(fields[24].trim());
        cvmConditionRepository.save(cvmCondition);
        cvmDetails.setCvmCondition(cvmCondition);

        CVMRuleResult cvmRuleResult = new CVMRuleResult();
        cvmRuleResult.setCvmRuleResultCode(Integer.parseInt(fields[25].trim()));
        cvmRuleResult.setCvmRuleResultDescription(fields[26].trim());
        cvmRuleResultRepository.save(cvmRuleResult);
        cvmDetails.setCvmRuleResult(cvmRuleResult);

        cvmDetailsRepository.save(cvmDetails);

        // TODO: pan entry mode code not mapped
        // fields[27].trim()

        // TODO: pan entry mode description not mapped
        // fields[28].trim()

        cardTransaction.setCvmDetails(cvmDetails);

        return cardTransaction;
    }
}