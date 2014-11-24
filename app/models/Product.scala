package models

case class Product(id: String, name: String, description: String)

object Product {
  var products = Set(
    Product("1", "Product1", "Description of Product1"),
    Product("2", "Product2", "Description of Product2"),
    Product("3", "Product3", "Description of Product3")
  )

  def findAll: List[Product] = this.products.toList.sortBy(_.id)
  def findById(id: String): Option[Product] = this.products.find(_.id == id)
  def save(product: Product): Product = findById(product.id) match {
    case Some(p) => {
      this.products = this.products - p + product
      product
    }
    case None => throw new IllegalArgumentException("No such product")
  }
  def insert(product: Product): Product = findById(product.id) match {
    case Some(p) => throw new IllegalArgumentException("Product already exists")
    case None => {
      this.products = this.products + product
      product
    }
  }
}