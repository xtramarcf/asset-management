//package de.fortmeier.asset_management.asset;
//
//import de.fortmeier.asset_management.assets.Asset;
//import de.fortmeier.asset_management.assets.AssetRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.context.TestPropertySource;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.List;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
//@Testcontainers
//class RepoTest {
//
//    @Container
//    static PostgreSQLContainer testContainer = new PostgreSQLContainer<>("postgres:13");
//
//    @DynamicPropertySource
//    static void properties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", testContainer::getJdbcUrl);
//        registry.add("spring.datasource.username", testContainer::getUsername);
//        registry.add("spring.datasource.password", testContainer::getPassword);
//    }
//
//    @Autowired
//    AssetRepository assetRepo;
//
//    @Test
//    void repoTest() {
//
//        assetRepo.save(new Asset());
//        List<Asset> assetList = assetRepo.findAll();
//        Assertions.assertFalse(assetList.isEmpty());
//    }
//}
