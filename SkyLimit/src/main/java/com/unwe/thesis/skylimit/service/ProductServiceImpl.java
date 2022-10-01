package com.unwe.thesis.skylimit.service;

import com.unwe.thesis.skylimit.model.entity.CategoryEntity;
import com.unwe.thesis.skylimit.model.entity.ProductEntity;
import com.unwe.thesis.skylimit.model.entity.UserEntity;
import com.unwe.thesis.skylimit.model.entity.UserRoleEntity;
import com.unwe.thesis.skylimit.model.entity.enums.CategoryEnum;
import com.unwe.thesis.skylimit.model.entity.enums.RoleEnum;
import com.unwe.thesis.skylimit.model.service.ProductAddServiceModel;
import com.unwe.thesis.skylimit.model.service.ProductUpdateServiceModel;
import com.unwe.thesis.skylimit.model.view.ProductViewModel;
import com.unwe.thesis.skylimit.repository.CategoryRepository;
import com.unwe.thesis.skylimit.repository.ProductRepository;
import com.unwe.thesis.skylimit.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl {
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public boolean existsByName(String name){
        return this.productRepository.existsByName(name);
    }

    public void addProduct(ProductAddServiceModel productAddServiceModel){
        ProductEntity product = this.modelMapper.map(productAddServiceModel, ProductEntity.class);
        CategoryEntity category = this.categoryRepository.findByCategory(productAddServiceModel.getCategory());
        product.setCategory(category);
        this.productRepository.save(product);

    }

    public List<ProductViewModel> getWaterAttractions(){
        List<ProductEntity> waterProducts = this.productRepository
                .findAllByCategory(this.categoryRepository.findByCategory(CategoryEnum.WATER));

        return waterProducts.stream().map(w -> this.modelMapper.map(w, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    public List<ProductViewModel> getAirAttractions(){
        List<ProductEntity> airProducts = this.productRepository
                .findAllByCategory(this.categoryRepository.findByCategory(CategoryEnum.AIR));

        return airProducts.stream().map(s -> this.modelMapper.map(s, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    public List<ProductViewModel> getCityAttractions(){
        List<ProductEntity> cityProducts = this.productRepository
                .findAllByCategory(this.categoryRepository.findByCategory(CategoryEnum.CITY));

        return cityProducts.stream().map(c -> this.modelMapper.map(c, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    public List<ProductViewModel> getLandAttractions(){
        List<ProductEntity> landProducts = this.productRepository
                .findAllByCategory(this.categoryRepository.findByCategory(CategoryEnum.LAND));

        return landProducts.stream().map(l -> this.modelMapper.map(l, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    public ProductViewModel findById(Long id) {
        Optional<ProductEntity> productOpt = this.productRepository.findById(id);
        ProductEntity productEntity = productOpt.get();
        ProductViewModel productViewModel = this.modelMapper.map(productEntity, ProductViewModel.class);
        productViewModel.setCategory(productEntity.getCategory().getCategory());

        return productViewModel;
    }

    public void updateProduct(ProductUpdateServiceModel productUpdateServiceModel) {
        ProductEntity productEntity = this.productRepository.findById(productUpdateServiceModel.getId()).orElse(null);

        productEntity.setPrice(productUpdateServiceModel.getPrice());
        productEntity.setAvailable(productUpdateServiceModel.getAvailable());
        productEntity.setLocation(productUpdateServiceModel.getLocation());
        productEntity.setDescription(productUpdateServiceModel.getLocation());
        productEntity.setName(productUpdateServiceModel.getName());
        productEntity.setImageUrl(productUpdateServiceModel.getImageUrl());
        productEntity.setCategory(this.categoryRepository.findByCategory(productUpdateServiceModel.getCategory()));

        this.productRepository.save(productEntity);
    }

    public void deleteProduct(Long id) {
        this.productRepository.deleteById(id);
    }

    public boolean isAdmin(String username){
        UserEntity userEntity = this.userRepository.findByUsername(username).orElse(null);
        return userEntity.getRoles().stream().map(UserRoleEntity::getRole).anyMatch(r -> r == RoleEnum.ADMIN);
    }





    public void seedProducts() {
        if(productRepository.count() == 0){
            ProductEntity jetRiding = new ProductEntity();
            jetRiding.setCategory(this.categoryRepository.findByCategory(CategoryEnum.WATER));
            jetRiding.setName("Управление на джет");
            jetRiding.setPrice(new BigDecimal("120.00"));
            jetRiding.setDescription("Освежете лятото със скоростно каране по морските вълни или в спокойните води на язовир." +
                    " Можете да избирате между джетове с различна мощност според Вашите предпочитания и опит. ");
            jetRiding.setLocation("Град Созопол и язовир Искър");
            jetRiding.setAvailable(true);
            jetRiding.setImageUrl("https://res.cloudinary.com/hoffenn/image/upload/v1664544239/SkyLimitPics/jet_cefddt.jpg");

            ProductEntity rafting = new ProductEntity();
            rafting.setCategory(this.categoryRepository.findByCategory(CategoryEnum.WATER));
            rafting.setName("Рафтинг");
            rafting.setPrice(new BigDecimal("80.00"));
            rafting.setDescription("Спускане с рафт по бързеите на река Струма е чудесен начин да превърнете скучния уикенд в незабравимо," +
                    " изпълнено с адреналин приключение." +
                    " Заредете се със смелост и състезателен дух," +
                    " защото Ви очaкват много предизвикателства в Кресненското дефиле." +
                    " След спускането всички ще бъдете изпълнени с енергия и положителни емоции. ");

            rafting.setLocation("Кресненско дефиле, река Струма");
            rafting.setAvailable(true);
            rafting.setImageUrl("https://res.cloudinary.com/hoffenn/image/upload/v1664544540/SkyLimitPics/rafting_la9f9e.webp");

            ProductEntity airBalloon = new ProductEntity();
            airBalloon.setCategory(this.categoryRepository.findByCategory(CategoryEnum.AIR));
            airBalloon.setName("Полет с балон");
            airBalloon.setPrice(new BigDecimal("136.00"));
            airBalloon.setDescription("Посрещнете изгрева високо в облаците, заедно с любимите си хора!\n" +
                    "Участниците в полет с топловъздушен балон ще имат възможността да се насладят на България от птичи поглед" +
                    " и буквално да отлетят накъдето ги отвее вятърът.");
            airBalloon.setLocation("В район Плиска/Мадара");
            airBalloon.setAvailable(true);
            airBalloon.setImageUrl("https://res.cloudinary.com/hoffenn/image/upload/v1664544521/SkyLimitPics/air-balloon_vyzwiw.jpg");

            ProductEntity parachute = new ProductEntity();
            parachute.setCategory(this.categoryRepository.findByCategory(CategoryEnum.AIR));
            parachute.setName("Скок с парашут");
            parachute.setPrice(new BigDecimal("300.00"));
            parachute.setDescription("Адреналинът в кръвта ще бушува дълго след скока," +
                    " а споменът ще остане за цял живот! Невъзможно е да опишем чувствата," +
                    " които ще ви връхлетят, когато скочите от самолета," +
                    " но обещаваме, че ще бъде незабравимо!");
            parachute.setLocation("В района на Монатана");
            parachute.setAvailable(true);
            parachute.setImageUrl("https://res.cloudinary.com/hoffenn/image/upload/v1664544537/SkyLimitPics/parachute_efw9lx.jpg");

            ProductEntity escapeRoom = new ProductEntity();
            escapeRoom.setCategory(this.categoryRepository.findByCategory(CategoryEnum.CITY));
            escapeRoom.setName("Стая със загадки");
            escapeRoom.setPrice(new BigDecimal("89.00"));
            escapeRoom.setDescription("Потопете се в интригуващ сценарий," +
                    " в който заобиколени от автентичен интериор," +
                    " ще съпреживеете едни от най-вълнуващите моменти." +
                    " Ще са Ви нужни: въображение, наблюдателност," +
                    " съобразителност, хитрост, бърза мисъл," +
                    " търпение и отборният дух на поне 2-ма играчи.");
            escapeRoom.setLocation("Град София");
            escapeRoom.setAvailable(true);
            escapeRoom.setImageUrl("https://res.cloudinary.com/hoffenn/image/upload/v1664544531/SkyLimitPics/escape-room_tgpwmp.jpg");

            ProductEntity shootingLesson = new ProductEntity();
            shootingLesson.setCategory(this.categoryRepository.findByCategory(CategoryEnum.CITY));
            shootingLesson.setName("Урок по стрелба");
            shootingLesson.setPrice(new BigDecimal("90.00"));
            shootingLesson.setDescription("Вашият инструктор ще Ви посвети в основите на безопасната и прецизна стрелба." +
                    " Стрелбището разполага с четири коридора за стрелба с дължина 25 метрa," +
                    " с възможност за стрелба от различни позиции и разстояние." +
                    " Мишените са хартиени, картонени." +
                    "  Не е необходимо да сте завършили курс за боравене с огнестрелни оръжия");
            shootingLesson.setLocation("Град София");
            shootingLesson.setAvailable(true);
            shootingLesson.setImageUrl("https://res.cloudinary.com/hoffenn/image/upload/v1664544543/SkyLimitPics/shooting_fflinw.jpg");

            ProductEntity bungeeJumping = new ProductEntity();
            bungeeJumping.setCategory(this.categoryRepository.findByCategory(CategoryEnum.LAND));
            bungeeJumping.setName("Скок с бънджи");
            bungeeJumping.setPrice(new BigDecimal("69.00"));
            bungeeJumping.setDescription("Подарете едно от най-вълнуващите преживявания, познати на света!" +
                    " Провокирайте смелостта и куража с доза адреналин или скочете в тандем с любимия човек. ");
            bungeeJumping.setLocation("Село Буново");
            bungeeJumping.setAvailable(true);
            bungeeJumping.setImageUrl("https://res.cloudinary.com/hoffenn/image/upload/v1664544526/SkyLimitPics/bungee_gqvty4.webp");

            ProductEntity horseRiding = new ProductEntity();
            horseRiding.setCategory(this.categoryRepository.findByCategory(CategoryEnum.LAND));
            horseRiding.setName("Конна езда на открито");
            horseRiding.setPrice(new BigDecimal("47.00"));
            horseRiding.setDescription("Отпуснете се и се отдайте на удоволствието от контакта и общуването с конете." +
                    " Ще се научите да следвате техните движения," +
                    " за да стане ездата лесна и естествена." +
                    " За това ще ви помогне квалифициран инструктор по езда с подходящ ездитен кон.");
            horseRiding.setLocation("Планина Витоша");
            horseRiding.setAvailable(true);
            horseRiding.setImageUrl("https://res.cloudinary.com/hoffenn/image/upload/v1664544534/SkyLimitPics/horse_rg7qka.jpg");


            this.productRepository.saveAll(Set.of(jetRiding, rafting, airBalloon,
                    parachute, escapeRoom, shootingLesson,
                    bungeeJumping, horseRiding));
        }
    }




    //TODO: make buy button and order info page
    // make about us / contacts page
    // test database triggers


    //flyboard link - https://res.cloudinary.com/hoffenn/image/upload/v1664544957/SkyLimitPics/flyboard_mu1vjy.webp
}
