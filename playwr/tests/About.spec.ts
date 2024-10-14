import { test, expect } from '@playwright/test';
import { urlAbout } from '../utils/links';

const helper = "npx playwright test tests/About.spec.ts --project=chromium";

test.use({ viewport: { width: 1420, height: 800 }});

test.beforeEach(async ({ page }) => {
    await page.goto(urlAbout);
});

test("039-VerifyThatTheImageInTheHeroIsNotDisplayedScreenWidthBelow768px", async ({ page }) => {
    await page.setViewportSize({
        width: 600,
        height: 800
    });

    await expect(page.locator("section.about-one").getByAltText("Fancy Person")).not.toBeVisible();
});

test("040-VerifyThatTheImageInTheHeroIsDisplayedScreenWidthAbove768px", async ({ page }) => {
    await expect(page.locator("section.about-one").getByAltText("Fancy Person")).toBeVisible();
});

test("041-VerifyAboutMeTextIsDisplayed @smoke", async ({ page }) => {
    await expect(page.locator("section.about-two .info-div p")).toBeVisible();
});