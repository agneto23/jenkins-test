import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser('')

WebUI.navigateToUrl('https://aplpre.favorita.ec/auth/realms/CFAVORITA-SSO-INTRANET/protocol/openid-connect/auth?response_type=code&client_id=login&redirect_uri=http%3A%2F%2Faplpre.favorita.ec%2Flogin%2F&state=cd1434ca-2387-421d-b375-eb6695cbaf48&login=true&scope=openid')

WebUI.setText(findTestObject('Object Repository/Page_Menu Principal/input_Usuario_username'), 'smxadmin')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_Menu Principal/input_Contrasea_password'), 'p4y+y39Ir5N2xSAc40hjcA==')

WebUI.click(findTestObject('Object Repository/Page_Menu Principal/input_Olvid su contrasea_login'))

WebUI.click(findTestObject('Object Repository/Page_Menu Principal/a_Cerrar sesin'))

WebUI.closeBrowser()

