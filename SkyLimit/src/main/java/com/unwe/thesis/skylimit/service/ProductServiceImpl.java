package com.unwe.thesis.skylimit.service;

import com.unwe.thesis.skylimit.model.entity.CategoryEntity;
import com.unwe.thesis.skylimit.model.entity.ProductEntity;
import com.unwe.thesis.skylimit.model.entity.enums.CategoryEnum;
import com.unwe.thesis.skylimit.model.service.ProductAddServiceModel;
import com.unwe.thesis.skylimit.model.view.ProductViewModel;
import com.unwe.thesis.skylimit.repository.CategoryRepository;
import com.unwe.thesis.skylimit.repository.ProductRepository;
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

    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
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
        List<ProductEntity> skyProducts = this.productRepository
                .findAllByCategory(this.categoryRepository.findByCategory(CategoryEnum.AIR));

        return skyProducts.stream().map(s -> this.modelMapper.map(s, ProductViewModel.class))
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
            jetRiding.setImageUrl("https://waterskireviews.com/wp-content/uploads/2017/09/Best-Kids-Water-Skis-in-2018-1-1.jpg");

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
            rafting.setImageUrl("https://cdn.shopify.com/s/files/1/2177/2669/products/flight666_1024x1024.jpg?v=1616507933");

            ProductEntity airBalloon = new ProductEntity();
            airBalloon.setCategory(this.categoryRepository.findByCategory(CategoryEnum.AIR));
            airBalloon.setName("Полет с балон");
            airBalloon.setPrice(new BigDecimal("136.00"));
            airBalloon.setDescription("Посрещнете изгрева високо в облаците, заедно с любимите си хора!\n" +
                    "Участниците в полет с топловъздушен балон ще имат възможността да се насладят на България от птичи поглед" +
                    " и буквално да отлетят накъдето ги отвее вятърът.");
            airBalloon.setLocation("В район Плиска/Мадара");
            airBalloon.setAvailable(true);
            airBalloon.setImageUrl("https://cf.bstatic.com/xdata/images/xphoto/2048x1536/123806704.jpg?k=ee390765951cff7caf2b05e61c8f56ab3f8a5c2b689279616cc8f94fb81184be&o=");

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
            parachute.setImageUrl("https://www.giftyland.com/32346-thickbox_default/tandem-parachute-jump-in-rapla.jpg");

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
            escapeRoom.setImageUrl("https://www.mysticescaperoom.com/Assets/Rooms2/AOT-3.jpg");

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
            shootingLesson.setImageUrl("https://assets.c2tactical.com/dev/wp-content/uploads/2020/04/01163118/CCW-1026x400.jpg");

            ProductEntity bungeeJumping = new ProductEntity();
            bungeeJumping.setCategory(this.categoryRepository.findByCategory(CategoryEnum.LAND));
            bungeeJumping.setName("Скок с бънджи");
            bungeeJumping.setPrice(new BigDecimal("69.00"));
            bungeeJumping.setDescription("Подарете едно от най-вълнуващите преживявания, познати на света!" +
                    " Провокирайте смелостта и куража с доза адреналин или скочете в тандем с любимия човек. ");
            bungeeJumping.setLocation("Село Буново");
            bungeeJumping.setAvailable(true);
            bungeeJumping.setImageUrl("https://images.chinahighlights.com/allpicture/2020/12/4bdd3e7fc61a43779b4f423b_cut_800x500_10.jpg");

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
            horseRiding.setImageUrl("https://dynamic-media-cdn.tripadvisor.com/media/photo-o/08/1c/6c/35/horseriding-bulgaria.jpg?w=500&h=400&s=1");


            this.productRepository.saveAll(Set.of(jetRiding, rafting, airBalloon,
                    parachute, escapeRoom, shootingLesson,
                    bungeeJumping, horseRiding));
        }
    }

    public ProductViewModel findById(Long id) {
        Optional<ProductEntity> productOpt = this.productRepository.findById(id);
        return this.modelMapper.map(productOpt, ProductViewModel.class);
    }


    //TODO:
    // make the details page, make edit and delete buttons (check if they work),
    // make edit and delete visible only to admin,
    // make about us / contacts page,
}
