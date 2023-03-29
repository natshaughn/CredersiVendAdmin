import { faker } from '@faker-js/faker';

describe("Credersi Vend Admin Acceptance Test Script", () => {
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

  //
  // Create a new customer
  //
  it("newCustomerTrueDetails", () => {
    // The variable to type into the specified input
    const customer = "testCustomer";
    // Checks you're on the correct page after logging in
    cy.get("button").should("have.text", "Create Customer");
    // Clicks on the create customer button
    cy.get("button").click();
    // Gets the input element and types in the customer
    cy.get("#placeholder1").type(`${customer}`);
    // Clicks on the ok button 
    cy.get("button.svelte-1fd0obp").contains("OK").click()
    // Checks that the new customer has been added
    cy.get("item:nth-child(1) displayable span").contains("testCustomer")
    cy.get("item").should("have.length", "4");
  });
  
  ///
  // Create customer then click cancel button 
  //
  it("cancelCustomer", () => {
    // Checks you're on the correct page after logging in
    cy.get("button").should("have.text", "Create Customer");
    // Clicks on the create customer button
    cy.get("button").click();
    // Clicks on the Cancel button
    cy.get("button.svelte-1fd0obp").contains("Cancel").click()
    // Checks that no customer has been added
    cy.get("item:nth-child(1) displayable span").should("have.text", "Alfa Ltd")
    cy.get("items item").should("have.length", "4");
    cy.get("button").should("have.text", "Create Customer");
    cy.get("button").should("not.have.text", "Cancel");
  });

  //
  // Create a new site
  //
  it("newSiteTrueDetails", () => {
    // The variables to type into the specified inputs
    const site = "testSiteName";
    const address = "testSiteAddress";
    // Checks you're on the correct page after logging in
    cy.get("button").should("have.text", "Create Customer");
    // Clicks on a customer that has already been created
    cy.get("#a7082b2f-12eb-49d2-a0ea-03885d2f3691").click();
    // Checks you are on the correct page by checking the specified button exists
    cy.get("button").should("have.text", "Create Site");
    // Clicks on the create site button
    cy.get("button").click();
    // Types the site and address variables into the input elements
    cy.get("#placeholder1").type(`${site}`);
    cy.get("#placeholder2").type(`${address}`);
    // Clicks on the ok button 
    cy.get("button.svelte-1fd0obp").contains("OK").click()
    // Check site has been created
    cy.get("items item:nth-child(1) displayable span b").should("contain.text", "testSiteName") 
    cy.get("items item:nth-child(1) displayable span").should("contain.text", "testSiteName testSiteAddress") 
    cy.get("items item").should("have.length", "3");
  }); 

  //
  // Create site then click cancel button 
  //
  it("cancelSite", () => {
    // Checks you're on the correct page after logging in
    cy.get("button").should("have.text", "Create Customer");
    // Clicks on a customer that has already been created
    cy.get("#a7082b2f-12eb-49d2-a0ea-03885d2f3691").click();
    // Checks you are on the correct page by checking the specified button exists
    cy.get("button").should("have.text", "Create Site");
    // Clicks on the create site button
    cy.get("button").click();
    // Clicks on cancel
    cy.get("button.svelte-1fd0obp").contains("Cancel").click()
    // Check site has been created
    cy.get("items item:nth-child(1) displayable span").should("have.text", "testSiteName testSiteAddress")
    cy.get("items item").should("have.length", "3");
    cy.get("button").should("have.text", "Create Site");
    cy.get("button").should("not.have.text", "Cancel");
  });

  //
  // Create a new machine 
  //
  it("newMachineTrueDetails", () => {
    // The variables to type into the specified inputs
    const machine = "TestMachineName";
    const location = "TestMachineLocation";
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
      cy.get("#placeholder2").type(`${location}`);
      // Clicks on the ok button 
      cy.get("button.svelte-1fd0obp").contains("OK").click()
      // Check machine has been created
      cy.get("items item:nth-child(1) displayable span").contains("TestMachineName TestMachineLocation" );
      cy.get("items item").should("have.length", "3");
  });

  //
  // Create machine then click cancel button 
  //
  it("cancelMachine", () => {
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
      // Click on cancel
      cy.get("button.svelte-1fd0obp").contains("Cancel").click()
      // Check machine has been created
      cy.get("items item:nth-child(1) displayable span").contains("TestMachineName TestMachineLocation");
      cy.get("items item").should("have.length", "3");
  });
});