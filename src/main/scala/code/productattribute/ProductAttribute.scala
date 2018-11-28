package code.productattribute

/* For ProductAttribute */

import code.model.BankId
import code.productAttributeattribute.MappedProductAttributeProvider
import code.productattribute.ProductAttribute.{ProductAttribute, ProductAttributeType}
import code.products.Products.ProductCode
import net.liftweb.common.{Box, Logger}
import net.liftweb.util.SimpleInjector

import scala.concurrent.Future

object ProductAttribute extends SimpleInjector {

  object ProductAttributeType extends Enumeration{
    type ProductAttributeType = Value
    val STRING = Value("STRING")

    val INTEGER = Value("INTEGER")

    val DOUBLE = Value("DOUBLE")

    val DATE_WITH_DAY = Value("DATE_WITH_DAY")
  }



  trait ProductAttribute {
    def bankId: BankId

    def productCode: ProductCode

    def productAttributeId: String

    def name: String

    def attributType: ProductAttributeType.Value

    def value: String
  }


  val productAttributeProvider = new Inject(buildOne _) {}

  def buildOne: ProductAttributeProvider = MappedProductAttributeProvider

  // Helper to get the count out of an option
  def countOfProductAttribute(listOpt: Option[List[ProductAttribute]]): Int = {
    val count = listOpt match {
      case Some(list) => list.size
      case None => 0
    }
    count
  }


}

trait ProductAttributeProvider {

  private val logger = Logger(classOf[ProductAttributeProvider])

  def getProductAttributesFromProvider(bank: BankId, productCode: ProductCode): Future[Box[List[ProductAttribute]]]

  def getProductAttributeById(productAttributeId: String): Future[Box[ProductAttribute]]

  def createOrUpdateProductAttribute(bankId : BankId,
                                     productCode: ProductCode,
                                     productAttributeId: Option[String],
                                     name: String,
                                     attributType: ProductAttributeType.Value,
                                     value: String): Future[Box[ProductAttribute]]
  def deleteProductAttribute(productAttributeId: String): Future[Box[Boolean]]
  // End of Trait
}






