import { test, expect } from '@playwright/test';
import { urlQaProjects } from '../utils/links';

const helper = "npx playwright test tests/QaProjectsPage.spec.ts --project=chromium";

test.use({ viewport: { width: 1420, height: 800 }});

test.beforeEach(async ({ page }) => {
    await page.goto(urlQaProjects);
});

test("026-VerifyThatTheImageInTheHeroIsNotDisplayedScreenWidthBelow768px", async ({ page }) => {
    await page.setViewportSize({
        width: 600,
        height: 800
    });

    await expect(page.getByAltText("Fancy Person")).not.toBeVisible();
});

test("027-VerifyThatTheImageInTheHeroIsDisplayedScreenWidthAbove768px", async ({ page }) => {
    await expect(page.getByAltText("Fancy Person")).toBeVisible();
});

test("028-VerifyTheTitleIsNbTestingProjects", async ({ page }) => {
    const correctTitle = "NB Testing Projects";
    await expect(page.getByRole("heading", { name: "NB Testing Projects" })).toBeVisible();
    expect(await page.getByRole("heading", { name: "NB Testing Projects" }).textContent()).toEqual(correctTitle);
});

test("029-VerifyTheProjectsAreDisplayed @smoke", async ({ page }) => {
    const numberOfProjects = await page.locator(".card").count();
    await expect(numberOfProjects).toBeGreaterThan(0);
});

test("030-VerifyTheFiltersAreNotDisplayed", async ({ page }) => {
    await expect(page.locator(".Filters__Wrapper-sc-uerh6j-0")).not.toBeVisible();
});