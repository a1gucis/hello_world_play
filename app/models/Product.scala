package models

case class Product(ean: Long, name: String, description: String)

object Product {
  var products = Set(
    Product(1L, "Product1", "Description of Product1"),
    Product(2L, "Product2", "Description of Product2"),
    Product(3L, "Product3", "Description of Product3")
  )

  def maxId: Long = products.reduce((p1, p2) => if (p1.ean > p2.ean) p1 else p2).ean
  def findAll: List[Product] = this.products.toList.sortBy(_.ean)
  def findByEan(ean: Long): Option[Product] = this.products.find(_.ean == ean)
  def save(product: Product): Product = findByEan(product.ean) match {
    case Some(p) => {
      this.products = this.products - p + product
      product
    }
    case None => throw new IllegalArgumentException("No such product")
  }
  def insert(product: Product): Product = findByEan(product.ean) match {
    case Some(p) => throw new IllegalArgumentException("Product already exists")
    case None => {
      this.products = this.products + product
      product
    }
  }
}