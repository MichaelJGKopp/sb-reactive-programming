package io.michaeljgkopp.github.sbwebfluxdemo.router;

import io.michaeljgkopp.github.sbwebfluxdemo.dto.Customer;
import io.michaeljgkopp.github.sbwebfluxdemo.handler.CustomerHandler;
import io.michaeljgkopp.github.sbwebfluxdemo.handler.CustomerStreamHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

  private final CustomerHandler customerHandler;
  private final CustomerStreamHandler customerStreamHandler;

  public RouterConfig(CustomerHandler customerHandler,
      CustomerStreamHandler customerStreamHandler) {
    this.customerHandler = customerHandler;
    this.customerStreamHandler = customerStreamHandler;
  }

  @Bean
  @RouterOperations( // for OpenAPI documentation
      {
          @RouterOperation(
              path = "/router/customers",
              produces = {MediaType.APPLICATION_JSON_VALUE},
              method = RequestMethod.GET,
              beanClass = CustomerHandler.class,
              beanMethod = "loadCustomers",
              operation = @Operation(
                  operationId = "loadCustomers",
                  responses = {
                      @ApiResponse(
                          responseCode = "200",
                          description = "successful operation",
                          content = @Content(
                              schema = @Schema(
                                  implementation = Customer.class
                              )))})
          ),
          @RouterOperation(
              path = "/router/customers/stream",
              produces = {MediaType.APPLICATION_JSON_VALUE},
              method = RequestMethod.GET,
              beanClass = CustomerStreamHandler.class,
              beanMethod = "getCustomers",
              operation = @Operation(
                  operationId = "getCustomers",
                  responses = {
                      @ApiResponse(
                          responseCode = "200",
                          description = "successful operation",
                          content = @Content(
                              array = @ArraySchema(
                                  schema = @Schema(
                                      implementation = Customer.class
                                  ))))})
          ),
          @RouterOperation(
              path = "/router/customers/{input}",
              produces = {MediaType.APPLICATION_JSON_VALUE},
              method = RequestMethod.GET,
              beanClass = CustomerHandler.class,
              beanMethod = "findCustomer",
              operation = @Operation(
                  operationId = "findCustomer",
                  responses = {
                      @ApiResponse(
                          responseCode = "200",
                          description = "successful operation",
                          content = @Content(
                              schema = @Schema(
                                  implementation = Customer.class
                              ))),
                      @ApiResponse(
                          responseCode = "404",
                          description = "Customer not found",
                          content = @Content(
                              schema = @Schema(
                                  implementation = Customer.class
                              )))},
                  parameters = {
                      @Parameter(
                          in = ParameterIn.PATH,
                          name = "input"
//                          , description = "Customer ID",
//                          required = true,
//                          schema = @Schema(
//                              type = "string"
//                          )
                      )})
          ),
          @RouterOperation(
              path = "/router/customers",
              produces = {MediaType.APPLICATION_JSON_VALUE},
              method = RequestMethod.POST,
              beanClass = CustomerHandler.class,
              beanMethod = "saveCustomer",
              operation = @Operation(
                  operationId = "saveCustomer",
                  responses = {
                      @ApiResponse(
                          responseCode = "201",
                          description = "successful operation",
                          content = @Content(
                              schema = @Schema(
                                  implementation = Customer.class
                              )))},
                  requestBody = @RequestBody(
                      required = true,
                      content = @Content(
                          schema = @Schema(
                              implementation = Customer.class
                          )))
              )
          )
      })
  public RouterFunction<ServerResponse> routerFunction() {
    return RouterFunctions.route()
        .GET("/router/customers", customerHandler::loadCustomers)
        .GET("/router/customers/stream", customerStreamHandler::getCustomers)
        .GET("/router/customers/{input}", customerHandler::findCustomer)
        .POST("/router/customers", customerHandler::saveCustomer)
        // can add POST, PUT, DELETE, etc. here
        .build();
  }
}
