package com.example.CreditCardApplication.controller;

import com.example.CreditCardApplication.persistence.Model.CardInfo;
import com.example.CreditCardApplication.requestDTO.CardRequestDTO;
import com.example.CreditCardApplication.service.CreditCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.CreditCardApplication.Utils.Constants.API_VERSION_1_0_0;

@Api(tags={"1. Credit Card Controller"})
@AllArgsConstructor
@RestController
@RequestMapping(API_VERSION_1_0_0 + "/cards")
public class CreditCardController {

    private CreditCardService creditCardService;

    @ApiOperation(value="Add Credit Card Information")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Credit card saved successfully"),
            @ApiResponse(
                    code = 500,
                    message = "Server error"),
            @ApiResponse(
                    code = 422,
                    message = "Unprocessable Entity"
            )})
    @PostMapping(value = "/add", produces = "application/json")
    public ResponseEntity saveProduct(@RequestBody CardRequestDTO request) {
        BindingResult errors = new BeanPropertyBindingResult(null, "");
        if (creditCardService.validateCardRequest(request, errors)) {
            creditCardService.processCardInformation(request, errors);
        }

        if (errors.hasErrors()) {
            return new ResponseEntity(errors.getAllErrors(), HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            return new ResponseEntity("Credit card saved successfully", HttpStatus.CREATED);
        }

    }

    @ApiOperation(value="Add Credit Card Information")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Retrieved Data successfully"),
            @ApiResponse(
                    code = 404,
                    message = "Data Not found"
            )})
    @GetMapping(value = "/getAll", produces = "application/json")
    public ResponseEntity<List<CardInfo>> getAllCards() {
        List<CardInfo> cardInfoList=creditCardService.getAll();

        if(CollectionUtils.isEmpty(cardInfoList))
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else
            return new ResponseEntity<>(cardInfoList,HttpStatus.OK);
    }

}
