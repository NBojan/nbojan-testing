import { test, expect } from '@playwright/test';
import { url } from '../utils/links';

const helper = "npx playwright test tests/ProjectCard.spec.ts --project=chromium";

test.beforeEach(async ({ page }) => {
    await page.goto(url);
});

const featuredProjectsContainerLocator = "//h2[text()='featured projects']/ancestor::section";
const featuredQaProjectsContainerLocator = "//h2[text()='featured QA projects']/ancestor::section";
const nbPortfolioCardLocator = "//h3[text()='nb portfolio']/parent::header/parent::div/parent::div";
const nbTwitterCardLocator = "//h3[text()='nb twitter']/parent::header/parent::div/parent::div";

test.describe("TestBelow768pxScreenWidth", () => {
    test.use({ viewport: { width: 600, height: 800 } });

    test("031-VerifyAWebProjectCardIncludesAnImageTitleDescrGithubAndPageLinkOnScreenWidthBelow768px @smoke", async ({ page }) => {
        const featuredProjectsContainer = page.locator(featuredProjectsContainerLocator);
        const projectCard = featuredProjectsContainer.locator(nbPortfolioCardLocator);

        await expect(projectCard.locator("div.active").getByAltText("Preview")).toBeVisible();
        await expect(projectCard.locator(".card-title")).toBeVisible();
        await expect(projectCard.locator(".desc")).toBeVisible();
        await expect(projectCard.getByRole("link", { name: "Github" })).toBeVisible();
        await expect(projectCard.getByRole("link", { name: "Visit" })).toBeVisible();
        
    });

    test("032-VerifyAQaProjectCardIncludesAnImageTitleDescrGithubLinkAndTypesOfTestingUsedOnScreenWidthBelow768px @smoke", async ({ page }) => {
        const featuredQaProjectsContainer = page.locator(featuredQaProjectsContainerLocator);
        const projectCard = featuredQaProjectsContainer.locator(nbTwitterCardLocator);

        await expect(projectCard.locator("div.active").getByAltText("Preview")).toBeVisible();
        await expect(projectCard.locator(".card-title")).toBeVisible();
        await expect(projectCard.locator(".desc")).toBeVisible();
        await expect(projectCard.getByRole("link", { name: "Github" })).toBeVisible();
        await expect(projectCard.locator("header p.mb-1")).toBeVisible();
    });
    
})

test.describe("TestAbove768pxScreenWidth", () => {
    test.use({ viewport: { width: 1420, height: 800 }});

    test("033-VerifyAWebProjectCardIncludesAnImageTitleDescrGithubAndPageLink @smoke", async ({ page }) => {
        const featuredProjectsContainer = page.locator(featuredProjectsContainerLocator);
        const projectCard = featuredProjectsContainer.locator(nbPortfolioCardLocator);

        await expect(projectCard.locator("div.active").getByAltText("Preview")).toBeVisible();
        await expect(projectCard.locator(".card-title")).toBeVisible();
        await expect(projectCard.locator(".desc")).toBeVisible();
        await expect(projectCard.getByRole("link", { name: "Github" })).toBeVisible();
        await expect(projectCard.getByRole("link", { name: "Visit" })).toBeVisible();
    });

    test("034-VerifyAQaProjectCardIncludesAnImageTitleDescrGithubLinkAndTypesOfTestingUsed @smoke", async ({ page }) => {
        const featuredQaProjectsContainer = page.locator(featuredQaProjectsContainerLocator);
        const projectCard = featuredQaProjectsContainer.locator(nbTwitterCardLocator);

        await expect(projectCard.locator("div.active").getByAltText("Preview")).toBeVisible();
        await expect(projectCard.locator(".card-title")).toBeVisible();
        await expect(projectCard.locator(".desc")).toBeVisible();
        await expect(projectCard.getByRole("link", { name: "Github" })).toBeVisible();
        await expect(projectCard.locator("header p.mb-1")).toBeVisible();
    });

    test("035-ValidateImageSwitchWhenClickingOnTheNextButton", async ({ page }) => {
        const featuredProjectsContainer = page.locator(featuredProjectsContainerLocator);
        const projectCard = featuredProjectsContainer.locator(nbPortfolioCardLocator);
        const currentImage = await projectCard.locator("div.active").getByAltText("Preview").getAttribute("src");

        await projectCard.locator(".chev-btn").last().click();
        await page.waitForTimeout(1100);
        const changedImage = await projectCard.locator("div.active").getByAltText("Preview").getAttribute("src");

        await expect(currentImage !== changedImage).toBeTruthy();
    });

    test("036-ValidateImageSwitchWhenClickingOnThePreviousButton", async ({ page }) => {
        const featuredProjectsContainer = page.locator(featuredProjectsContainerLocator);
        const projectCard = featuredProjectsContainer.locator(nbPortfolioCardLocator);
        const currentImage = await projectCard.locator("div.active").getByAltText("Preview").getAttribute("src");

        await projectCard.locator(".chev-btn").first().click();
        await page.waitForTimeout(1100);
        const changedImage = await projectCard.locator("div.active").getByAltText("Preview").getAttribute("src");

        await expect(currentImage !== changedImage).toBeTruthy();
    });

    test("037-ValidateClickingOnReadMoreOpensUpTheText", async ({ page }) => {
        const featuredProjectsContainer = page.locator(featuredProjectsContainerLocator);
        const projectCard = featuredProjectsContainer.locator(nbTwitterCardLocator);

        await projectCard.locator("//span[text()='read more']").click();
        await expect(projectCard.locator("//span[text()='read less']")).toBeVisible();
        await expect(await projectCard.locator(".desc span").first().getAttribute("class")).toContain("line-clamp-none");
    });

    test("038-ValidateClickingOnReadLessShortensTheText", async ({ page }) => {
        const featuredProjectsContainer = page.locator(featuredProjectsContainerLocator);
        const projectCard = featuredProjectsContainer.locator(nbTwitterCardLocator);

        await projectCard.locator("//span[text()='read more']").click();
        await expect(projectCard.locator("//span[text()='read less']")).toBeVisible();
        expect(await projectCard.locator(".desc span").first().getAttribute("class")).toContain("line-clamp-none");

        await projectCard.locator("//span[text()='read less']").click();
        await expect(projectCard.locator("//span[text()='read more']")).toBeVisible();
        expect(await projectCard.locator(".desc span").first().getAttribute("class")).toContain("line-clamp-2");
    });
})