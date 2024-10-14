import { test, expect } from '@playwright/test';
import { url, urlProjects, urlQaProjects } from '../utils/links';

const helper = "npx playwright test tests/Homepage.spec.ts --project=chromium";

test.use({ viewport: { width: 1420, height: 800 }});

test.beforeEach(async ({ page }) => {
    await page.goto(url);
});

test("011-VerifyThatTheImageInTheHeroIsNotDisplayedScreenWidthBelow768px", async ({ page }) => {
    await page.setViewportSize({
        width: 600,
        height: 800
    });

    await expect(page.getByAltText("Fancy Person")).not.toBeVisible();
});

test("012-VerifyThatTheImageInTheHeroIsDisplayedScreenWidthAbove768px", async ({ page }) => {
    await expect(page.getByAltText("Fancy Person")).toBeVisible();
});

test("013-VerifyTheFeaturedProjectsContainerIsDisplayed @smoke", async ({ page }) => {
    await expect(page.locator("//h2[text()='featured projects']/ancestor::section")).toBeVisible();
});

test("014-VerifyTheFeaturedQaProjectsContainerIsDisplayed @smoke", async ({ page }) => {
    await expect(page.locator("//h2[text()='featured QA projects']/ancestor::section")).toBeVisible();
});

test("015-ValidateTheLinkInsideTheFeaturedProjectsContainerRedirectsToTheProjectsPage", async ({ page }) => {
    const featuredProjectsContainer = page.locator("//h2[text()='featured projects']/ancestor::section");
    featuredProjectsContainer.getByRole("link", { name: 'See all projects' }).click();
    
    await expect(page.getByRole("heading", { name: "NBojan web projects" })).toBeVisible();
    await expect(page.url()).toEqual(urlProjects);
});

test("016-ValidateTheLinkInsideTheFeaturedQaProjectsContainerRedirectsToTheQaProjectsPage", async ({ page }) => {
    const featuredQaProjectsContainer = page.locator("//h2[text()='featured QA projects']/ancestor::section");
    featuredQaProjectsContainer.getByRole("link", { name: 'See all projects' }).click();
    
    await expect(page.getByRole("heading", { name: "NBojan testing projects" })).toBeVisible();
    await expect(page.url()).toEqual(urlQaProjects);
});