import { faker } from '@faker-js/faker';

describe("Credersi Vend Admin Test Script", () => {
  beforeEach(() => {
    // Visits localhost:8080 before running each test case and logs in
    cy.visit("http://localhost:8080/");
    // The variables to pass into specified login inputs
    const username = "admin";
    const password = "HelloWorld";
    // Checks the title to ensure you are on the correct page
    cy.get("title").should("have.text", "Credersi-vend Admin Login");
    // Enters the log in details
    cy.get("input#username").type(`${username}`);
    cy.get("input#password").type(`${password}`);
    // Clicks on login button 
    cy.get("button").contains("Login").click();
  });

  /*
  //
  // Create a new customer with blank details
  //
  it("newCustomerBlankDetails", () => {
    // Checks you're on the correct page after logging in
    cy.get("button").should("have.text", "Create Customer");
    // Clicks on the create customer button
    cy.get("button").click();
    // Inputs nothing and presses enter
    cy.get("#placeholder1").type('{enter}');
    // Checks that nothing should be added
    cy.get("items item:nth-child(1) displayable span").should("have.text", "Alfa Ltd")
    cy.get("items item").should("have.length", "2");
  });
  // Fail - add's customer as undefined
  */

  /*
  //
  // Create duplicate customers
  //
  it("newCustomerDuplicateDetails", () => {
    // The variables to type into the specified inputs
    const customer = "testCustomerDupe";
    const customerOne = "testCustomerDupe";
    // Checks you're on the correct page after logging in
    cy.get("button").should("have.text", "Create Customer");
    // Clicks on the create customer button
    cy.get("button").click();
    // Gets the input element and types in the customer and presses enter
    cy.get("#placeholder1").type(`${customer}{enter}`);
    // Checks the customer has been added
    cy.get("items item:nth-child(1) displayable span").should("have.text", "testCustomerDupe")
    cy.get("item").should("have.length", "4");
    // Clicks on the create customer button
    cy.get("button").click();
    // Gets the input element and types in the customer and presses enter
    cy.get("#placeholder1").type(`${customerOne}{enter}`);
    // Checks that the duplicate should not have been added
    cy.get("items item:nth-child(2) displayable span").should("have.text", "Alfa Ltd")
    cy.get("item").should("have.length", "4");
  });
  // Fail - Adds duplicate customers
  */

  /*
  //
  // Create a new site with blank details
  //
  it("newSiteBlankDetails", () => {
    // Checks you're on the correct page after logging in
    cy.get("button").should("have.text", "Create Customer");
    // Clicks on a customer that has already been created
    cy.get("#a7082b2f-12eb-49d2-a0ea-03885d2f3691").click();
    // Checks you are on the correct page by checking the specified button exists
    cy.get("button").should("have.text", "Create Site");
    // Clicks on the create site button
    cy.get("button").click();
    // Inputs nothing and presses enter
    cy.get("#placeholder1").type('{enter}');
    // Checks if the site has been created
    cy.get("items item:nth-child(1) displayable span").should("have.text", "Beta 24 Beta Lane, Alfatown, Alfashire, AL01 1BL");
    cy.get("item").should("have.length", "2");
  });
  // Fail - Adds site as undefined
  */

  /*
  //
  // Create duplicate sites
  //
  it("newSiteDuplicateDetails", () => {
    // The variables to type into the specified input
    const site = "testSiteNameDupe";
    const address = "testSiteAddressDupe";
    // Checks you're on the correct page after logging in
    cy.get("button").should("have.text", "Create Customer");
    // Clicks on a customer that has already been created
    cy.get("#a7082b2f-12eb-49d2-a0ea-03885d2f3691").click();
    // Checks you are on the correct page by checking the specified button exists
    cy.get("button").should("have.text", "Create Site");
    // Clicks on the create site button
    cy.get("button").click();
    // Type the site and address variables into the input elements
    cy.get("#placeholder1").type(`${site}`);
    cy.get("#placeholder2").type(`${address}{enter}`);
    cy.get("items item:nth-child(1) displayable span").should("have.text", "testSiteNameDupe testSiteAddressDupe");
    cy.get("items item").should("have.length", "3");
    // Click on the create site button
    cy.get("button").click();
    // Type the site and address variables into the input elements
    cy.get("#placeholder1").type(`${site}`);
    cy.get("#placeholder2").type(`${address}{enter}`);
    cy.get("items item:nth-child(2) displayable span").should("have.text", "Beta 24 Beta Lane, Alfatown, Alfashire, AL01 1BL");
    // Check sites has been created
    cy.get("items item").should("have.length", "3");
  });
  // Fail - adds duplicate sites
  */

  /*
  //
  // Create a new machine with blank details
  //
  it("newMachineBlankDetails", () => {
    // Checks you're on the correct page after logging in
    cy.get("button").should("have.text", "Create Customer");
      // Clicks on a customer that has already been created
      cy.get("#a7082b2f-12eb-49d2-a0ea-03885d2f3691").click();
      // Checks you are on the correct page by checking the specified button exists
      cy.get("button").should("have.text", "Create Site");
      // Clicks on an existing site 
      cy.get("#dc5e7463-c914-4507-b29f-87d9e2a59d88").click();
      // Checks you are on the correct page 
      cy.get("component item:nth-child(1) displayable span").contains("Ground Floor Reception");
      // Clicks on the only machine on this page
      cy.get("#c61bd599-969c-4c95-9018-5b67286ca93b").click();
      // Checks you are on the correct page by checking the specified button exists
      cy.get("button").should("have.text", "Create Machine");
      // Clicks on the create machine button
      cy.get("button").click();
      // Inputs nothing and presses enter
      cy.get("#placeholder1").type(`{enter}`);
      // Checks machine has not been created
      cy.get("items item:nth-child(1) displayable span").should("have.text", "Staff Machine Basement by stairs");
      cy.get("items item").should("have.length", "2");
  });
  // Fail - adds new machine as undefined
  */

  /*
  //
  // Create new machines with duplicate details
  //
  it("newMachineDuplicateDetails", () => {
    // The variables to type into the specified input
    const machine = "TestMachineNameDupe";
    const location = "TestMachineLocationDupe";
    // Checks you're on the correct page after logging in
    cy.get("button").should("have.text", "Create Customer");
      // Clicks on a customer that has already been created
      cy.get("#a7082b2f-12eb-49d2-a0ea-03885d2f3691").click();
      // Checks you are on the correct page by checking the specified button exists
      cy.get("button").should("have.text", "Create Site");
      // Clicks on an existing site 
      cy.get("#dc5e7463-c914-4507-b29f-87d9e2a59d88").click();
      // Checks you are on the correct page 
      cy.get("component item:nth-child(1) displayable span").contains("Ground Floor Reception");
      // Clicks on the only machine on this page
      cy.get("#c61bd599-969c-4c95-9018-5b67286ca93b").click();
      // Checks you are on the correct page by checking the specified button exists
      cy.get("button").should("have.text", "Create Machine");
      // Clicks on the create machine button
      cy.get("button").click();
      // Types the machine and location variables into the input elements
      cy.get("#placeholder1").type(`${machine}`);
      cy.get("#placeholder2").type(`${location}{enter}`);
      cy.get("items item:nth-child(1) displayable span").contains("TestMachineNameDupe TestMachineLocationDupe");
      cy.get("items item").should("have.length", "3");
      // Clicks on the create machine button
      cy.get("button").click();
      // Types the site and address variables into the input elements
      cy.get("#placeholder1").type(`${machine}`);
      cy.get("#placeholder2").type(`${location}{enter}`);
      // Checks the dupe machine should not been added
      cy.get("items item:nth-child(2) displayable span").should("have.text", "Staff Machine Basement by stairs");
      cy.get("items item").should("have.length", "3");
  });
  // Fail - adds the duplicate machine
  */

  /*
  //
  // Create a customer with 100 characters
  //
  it("newCustomerWith100Characters", () => {
    // Checks you're on the correct page after logging in
    cy.get("button").should("have.text", "Create Customer");
    // Clicks on the create customer button
    cy.get("button").click();
    for (let i = 0; i < 100; i++) {
      // Gets the input element and types in the customer and then presses enter
      cy.get("#placeholder1").type('T');
    }
    // Clicks on the ok button 
    cy.get("button.svelte-1fd0obp").contains("OK").click()
    // Checks that the new customer has been added
    cy.get("items item:nth-child(1) displayable span").should("have.text", "TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
    cy.get("items item").should("have.length", "4");
  });
  // PASS
  */

  /*
  //
  // Create 100 new customers 
  //
  it("create100Customers", () => {
    // Checks you're on the correct page after logging in
    cy.get("button").should("have.text", "Create Customer");
    for (let i = 0; i < 100; i++) {
      // Clicks on the create customer button
      cy.get("button").click();
      // Gets the input element and types in the customer and then presses enter
      cy.get("#placeholder1").type(`${faker.name.firstName()}{enter}`);
      cy.get("items item:nth-child(1) displayable span").should("not.have.text", "");
    }
    Checks all the customers have been added
    cy.get("items item").should("have.length", "103");
  });
  // PASS
  */

  // 
  // Create 100 new sites
  //
  it("create100Sites", () => {
    // Checks you're on the correct page after logging in
    cy.get("button").should("have.text", "Create Customer");
    // Clicks on a customer that has already been created
    cy.get("#a7082b2f-12eb-49d2-a0ea-03885d2f3691").click();
    // Checks you are on the correct page by checking the specified button exists
    cy.get("button").should("have.text", "Create Site");
    for (let i = 0; i < 100; i++) {
      // Clicks on the Create Site button
      cy.get("button").click();
      cy.get("#placeholder1").type(`${faker.name.jobDescriptor()}`);
      cy.get("#placeholder2").type(`${faker.address.streetAddress()}{enter}`);
      cy.get("items item:nth-child(1) displayable span").should("not.have.text", "");
    }
    // Checks all the sites have been added
    cy.get("items item").should("have.length", "102");
  });

  it("create100Machines", () => {
    // Checks you're on the correct page after logging in
    cy.get("button").should("have.text", "Create Customer");
      // Clicks on a customer that has already been created
      cy.get("#a7082b2f-12eb-49d2-a0ea-03885d2f3691").click();
      // Checks you are on the correct page by checking the specified button exists
      cy.get("button").should("have.text", "Create Site");
      // Clicks on an existing site 
      cy.get("#dc5e7463-c914-4507-b29f-87d9e2a59d88").click();
      // Checks you are on the correct page 
      cy.get("#c61bd599-969c-4c95-9018-5b67286ca93b").contains("Ground Floor Reception");
      // Clicks on the only machine on this page
      cy.get("#c61bd599-969c-4c95-9018-5b67286ca93b").click();
      // Checks you are on the correct page by checking the specified button exists
      cy.get("button").should("have.text", "Create Machine");
      for (let i = 0; i < 100; i++) {
        // Clicks on the create machine button
        cy.get("button").click();
        cy.get("#placeholder1").type(`${faker.name.jobType()}`);
        cy.get("#placeholder2").type(`${faker.address.cityName()}{enter}`);
        cy.get("items item:nth-child(1) displayable span").should("not.have.text", "");
      }
      cy.get("items item").should("have.length", "102");
  });
});
