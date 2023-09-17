// JDBConnection 파일을 상속 받으면 연결 자체는 JDB~가 해주겠죠?!
// JDBConnection을 사용해서 mysql에 접근하려면 라이브러리 로드 필요 => WEB-INF 폴더 안의 lib에 추가해주기
package shop.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shop.dto.Product;

public class ProductRepository extends JDBConnection {
	
	/**
	 * 상품 목록
	 * @return
	 */
	public List<Product> list() {
		
		ArrayList<Product> productList = new ArrayList<Product>();
		
		String sql = " SELECT * FROM product ";
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while( rs.next() ) {
				Product product = new Product();
				
				product.setProductId( rs.getString("product_id") );
				product.setName( rs.getString("name") );
				product.setUnitPrice( rs.getInt("unit_price") );
				product.setDescription( rs.getString("description") );
				product.setManufacturer( rs.getString("manufacturer") );
				product.setCategory( rs.getString("category") );
				product.setUnitsInStock( rs.getLong("units_in_stock") );
				product.setCondition( rs.getString("condition") );
				
				productList.add(product);
			}
			
		} catch (SQLException e) {
			System.err.println("상품 목록 조회 시, 에러 발생");
			e.printStackTrace();
		}
		return productList; 
	}
	
	/**
	 * 상품 조회
	 * @param productId
	 * @return
	 */
	public Product getProductById(String productId) {
		Product product = new Product();
		
		String sql = " SELECT * FROM product WHERE product_id = ? ";
		
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, productId);
			rs = psmt.executeQuery();
			
			if( rs.next() ) {
				product.setProductId( rs.getString("product_id") );
				product.setName( rs.getString("name") );
				product.setUnitPrice( rs.getInt("unit_price") );
				product.setDescription( rs.getString("description") );
				product.setManufacturer( rs.getString("manufacturer") );
				product.setCategory( rs.getString("category") );
				product.setUnitsInStock( rs.getLong("units_in_stock") );
				product.setCondition( rs.getString("condition") );
			}
		} catch (SQLException e) {
			System.err.println("상품 조회 시, 에러 발생");
			e.printStackTrace();
		}
		return product;
	}
	
	
	/**
	 * 상품 등록
	 * @param product
	 * @return
	 */
	public int insert(Product product) {
		int result = 0;
		
		String sql = " INSERT INTO product (product_id, name, unit_price, description, manufacturer, category, units_in_stock, `condition`) "
				   + " VALUES( ?, ?, ?, ?, ?, ?, ?, ?) ";
		
		int no = 1;
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(no++, product.getProductId());
			psmt.setString(no++, product.getName());
			psmt.setInt(no++, product.getUnitPrice());
			psmt.setString(no++, product.getDescription());
			psmt.setString(no++, product.getManufacturer());
			psmt.setString(no++, product.getCategory());
			psmt.setLong(no++, product.getUnitsInStock());
			psmt.setString(no++, product.getCondition());
			result = psmt.executeUpdate();			// 상품 등록 요청
			
		} catch (SQLException e) {
			System.err.println("상품 등록 중, 에러 발생!");
			e.printStackTrace();
		}
		System.out.println("상품 " + result + "개가 등록되었습니다.");
		return result;
	}

}





