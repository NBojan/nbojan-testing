import { test, expect } from '@playwright/test';
import { urlContact } from '../utils/links';

const helper = "npx playwright test tests/Contact.spec.ts --project=chromium";

const urlFormspree = "https://formspree.io/thanks?language=en";

test.use({ viewport: { width: 1420, height: 800 }});

test.beforeEach(async ({ page }) => {
    await page.goto(urlContact);
});

test("042-VerifyContactFormIsDisplayed @smoke", async ({ page }) => {
    await expect(page.locator("form")).toBeVisible();
});

test("043-ValidateSubmittingTheFormAfterInputtingValidData @smoke", async ({ page }) => {
    const form = page.locator("form");
    await form.locator("input[name='name']").fill("TestName");
    await form.locator("input[name='email']").fill("test@test.com");
    await form.locator("textarea[name='message']").fill("TestMessage");
    await form.getByRole("button", { name: "submit" }).click();

    await expect(page.locator("//p[text()='The form was submitted successfully.']")).toBeVisible();
    expect(page.url()).toEqual(urlFormspree);
});

test("044-ValidateFormWillNotSubmitAfterSubmittingWithoutFillingAnyField @smoke", async ({ page }) => {
    const form = page.locator("form");
    await form.getByRole("button", { name: "submit" }).click();

    await expect(page.locator("//p[text()='The form was submitted successfully.']")).not.toBeVisible();
    expect(page.url()).toEqual(urlContact);
});

test("045-ValidateFormWillNotSubmitAfterSubmittingWithoutInputtingAName", async ({ page }) => {
    const form = page.locator("form");
    await form.locator("input[name='email']").fill("test@test.com");
    await form.locator("textarea[name='message']").fill("TestMessage");
    await form.getByRole("button", { name: "submit" }).click();

    await expect(page.locator("//p[text()='The form was submitted successfully.']")).not.toBeVisible();
    expect(page.url()).toEqual(urlContact);
});

test("046-ValidateFormWillNotSubmitAfterSubmittingWithoutInputtingAnEmail", async ({ page }) => {
    const form = page.locator("form");
    await form.locator("input[name='name']").fill("TestName");
    await form.locator("textarea[name='message']").fill("TestMessage");
    await form.getByRole("button", { name: "submit" }).click();

    await expect(page.locator("//p[text()='The form was submitted successfully.']")).not.toBeVisible();
    expect(page.url()).toEqual(urlContact);
});

test.skip("047-ValidateFormWillNotSubmitAfterSubmittingWithoutInputtingAMessage", async ({ page }) => {
    const form = page.locator("form");
    await form.locator("input[name='name']").fill("TestName");
    await form.locator("input[name='email']").fill("test@test.com");
    await form.getByRole("button", { name: "submit" }).click();

    await expect(page.locator("//p[text()='The form was submitted successfully.']")).not.toBeVisible();
    expect(page.url()).toEqual(urlContact);
});

test("048-ValidateFormWillNotSubmitAfterInputtingWhiteSpacesInAllTheFields", async ({ page }) => {
    const form = page.locator("form");
    await form.locator("input[name='name']").fill(" ");
    await form.locator("input[name='email']").fill(" ");
    await form.locator("textarea[name='message']").fill(" ");
    await form.getByRole("button", { name: "submit" }).click();

    await expect(page.locator("//p[text()='The form was submitted successfully.']")).not.toBeVisible();
    expect(page.url()).toEqual(urlContact);
});

test.skip("049-ValidateFormWillNotSubmitAfterInputtingWhiteSpacesInTheNameField", async ({ page }) => {
    const form = page.locator("form");
    await form.locator("input[name='name']").fill(" ");
    await form.locator("input[name='email']").fill("test@test.com");
    await form.locator("textarea[name='message']").fill("TestMessage");
    await form.getByRole("button", { name: "submit" }).click();

    await expect(page.locator("//p[text()='The form was submitted successfully.']")).not.toBeVisible();
    expect(page.url()).toEqual(urlContact);
});

test("050-ValidateFormWillNotSubmitAfterInputtingWhiteSpacesInTheEmailField", async ({ page }) => {
    const form = page.locator("form");
    await form.locator("input[name='name']").fill("TestName");
    await form.locator("input[name='email']").fill(" ");
    await form.locator("textarea[name='message']").fill("TestMessage");
    await form.getByRole("button", { name: "submit" }).click();

    await expect(page.locator("//p[text()='The form was submitted successfully.']")).not.toBeVisible();
    expect(page.url()).toEqual(urlContact);
});

test.skip("051-ValidateFormWillNotSubmitAfterInputtingWhiteSpacesInTheMessageField", async ({ page }) => {
    const form = page.locator("form");
    await form.locator("input[name='name']").fill("TestName");
    await form.locator("input[name='email']").fill("test@test.com");
    await form.locator("textarea[name='message']").fill(" ");
    await form.getByRole("button", { name: "submit" }).click();

    await expect(page.locator("//p[text()='The form was submitted successfully.']")).not.toBeVisible();
    expect(page.url()).toEqual(urlContact);
});