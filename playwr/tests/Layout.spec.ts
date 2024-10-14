import { test, expect } from '@playwright/test';
import { url, urlAbout } from '../utils/links';

const helper = "npx playwright test tests/Layout.spec.ts --project=chromium";

test.beforeEach(async ({ page }) => {
    await page.goto(url);
});
  
test.describe("TestAbove768pxScreenWidth", () => {
    test.use({ viewport: { width: 1420, height: 800 }});

    test("001-VerifyLogoIsDisplayedInTheHeader @smoke", async ({ page }) => {
        await expect(page.getByAltText("NBojan")).toBeVisible();
    });

    test("002-ValidateTheLogoIsALinkToTheHomepage", async ({ page }) => {
        await page.goto(urlAbout);
        await page.locator("a.logo-div").click();
        await expect(page.getByRole("heading", { name: "Project page NBojan" })).toBeVisible();
        await expect(page.url()).toEqual(url);
    });

    test("003-VerifyTheHeaderLinksAreDisplayed @smoke", async ({ page }) => {
        await expect(page.locator("nav div.links")).toBeVisible();
    });
})

test.describe("TestBelow768pxScreenWidth", () => {
    test.use({ viewport: { width: 600, height: 800 } });

    test("004-VerifyTheLogoIsDisplayedOnScreenWidthBelow768px @smoke", async ({ page }) => {
        await expect(page.getByAltText("NBojan")).toBeVisible();
    });

    test("005-VerifyTheHeaderLinksAreNotDisplayedOnScreenWidthBelow768px", async ({ page }) => {
        await expect(page.locator("nav div.links")).not.toBeVisible();
    });

    test("006-VerifyTheBurgerMenuIsDisplayedOnScreenWidthBelow768px @smoke", async ({ page }) => {
        await expect(page.locator("button.burger-btn")).toBeVisible();
    });

    test("007-ValidateTheBurgerMenuButtonDisappearsAfterIncreasingScreenWidthAbove768px", async ({ page }) => {
        await expect(page.locator("button.burger-btn")).toBeVisible();
        await page.setViewportSize({
            width: 1024,
            height: 800
        });
        await expect(page.locator("button.burger-btn")).not.toBeVisible();
    });

    test("008-VerifyScrollToTopButtonIsInvisibleIfNoScrollApplied", async ({ page }) => {
        await expect(page.locator("button.hideScroll")).not.toBeVisible();
    });

    test("009-ValidateScrollToTopButtonIsVisibleIfScrolledToEndOfPage @smoke", async ({ page }) => {
        await expect(page.locator("button.hideScroll")).not.toBeVisible();
        await page.mouse.wheel(0, 10000);
        await expect(page.locator("button.showScroll")).toBeVisible();
    });

    test("010-ValidateScrollToTopScrollToTheTopOfThePage @smoke", async ({ page }) => {
        await expect(page.locator("button.hideScroll")).not.toBeVisible();
        await page.mouse.wheel(0, 10000);
        await expect(page.locator("button.showScroll")).toBeVisible();
        await page.locator("button.showScroll").click();
        await expect(page.getByRole("heading", { name: "Project page NBojan" })).toBeInViewport();
    });
})