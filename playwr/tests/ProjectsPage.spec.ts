import { test, expect } from '@playwright/test';
import { url, urlProjects } from '../utils/links';

const helper = "npx playwright test tests/ProjectsPage.spec.ts --project=chromium";

test.use({ viewport: { width: 1420, height: 800 }});

test.beforeEach(async ({ page }) => {
    await page.goto(urlProjects);
});

test("017-VerifyThatTheImageInTheHeroIsNotDisplayedScreenWidthBelow768px", async ({ page }) => {
    await page.setViewportSize({
        width: 600,
        height: 800
    });

    await expect(page.getByAltText("Fancy Person")).not.toBeVisible();
});

test("018-VerifyThatTheImageInTheHeroIsDisplayedScreenWidthAbove768px", async ({ page }) => {
    await expect(page.getByAltText("Fancy Person")).toBeVisible();
});

test("019-VerifyTheProjectsTitleIsNbWebProjects", async ({ page }) => {
    const correctTitle = "NB Web Projects";
    await expect(page.getByRole("heading", { name: "NB Web Projects" })).toBeVisible();
    expect(await page.getByRole("heading", { name: "NB Web Projects" }).textContent()).toEqual(correctTitle);
});

test("020-VerifyTheProjectsAreDisplayed @smoke", async ({ page }) => {
    const numberOfProjects = await page.locator(".card").count();
    await expect(numberOfProjects).toBeGreaterThan(0);
});

test("021-VerifyTheFiltersAreDisplayed @smoke", async ({ page }) => {
    await expect(page.locator(".Filters__Wrapper-sc-uerh6j-0")).toBeVisible();
});

test("022-ValidateTheFiltersAreWorkingByClickingOnAnyFilterExceptAll @smoke", async ({ page }) => {
    const numberOfProjects = await page.locator(".card").count();
    const filtersContainer = page.locator(".Filters__Wrapper-sc-uerh6j-0");

    await filtersContainer.getByRole("button", { name: "next" }).click();
    const numberOfProjectsAfterFilter = await page.locator(".card").count();
    await expect(numberOfProjectsAfterFilter < numberOfProjects).toBeTruthy();
});

test("023-ValidateTheAllFilterDisplaysAllTheProjectsAfterUsingADifferentFilter @smoke", async ({ page }) => {
    const numberOfProjects = await page.locator(".card").count();
    const filtersContainer = page.locator(".Filters__Wrapper-sc-uerh6j-0");

    await filtersContainer.getByRole("button", { name: "next" }).click();
    const numberOfProjectsAfterFilter = await page.locator(".card").count();
    await expect(numberOfProjectsAfterFilter < numberOfProjects).toBeTruthy();

    await filtersContainer.getByRole("button", { name: "all" }).click();
    const numberOfProjectsUsingAllFilter = await page.locator(".card").count();
    await expect(numberOfProjectsUsingAllFilter === numberOfProjects).toBeTruthy();
});

test("024-ValidateTheChosenFilterButtonHasAnActiveClass", async ({ page }) => {
    const filtersContainer = page.locator(".Filters__Wrapper-sc-uerh6j-0");

    const filterButton = filtersContainer.getByRole("button", { name: "next" });
    await filterButton.click();
    await expect(await filterButton.getAttribute("class")).toContain("active");
});

test("025-ValidateFilterResetsAfterSwitchingPageAndComingBack", async ({ page }) => {
    const numberOfProjects = await page.locator(".card").count();
    const filtersContainer = page.locator(".Filters__Wrapper-sc-uerh6j-0");

    await filtersContainer.getByRole("button", { name: "next" }).click();
    const numberOfProjectsAfterFilter = await page.locator(".card").count();
    await expect(numberOfProjectsAfterFilter < numberOfProjects).toBeTruthy();

    await page.goto(url);
    await expect(page.getByRole("heading", { name: "Project page NBojan" })).toBeVisible();

    await page.locator("nav").getByRole("link", { name: "projects", exact: true }).click();
    await expect(page.getByRole("heading", { name: "NBojan web projects" })).toBeVisible();

    const numberOfProjectsAfterReroute = await page.locator(".card").count();
    await expect(numberOfProjectsAfterReroute === numberOfProjects).toBeTruthy();
});