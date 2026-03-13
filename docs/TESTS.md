# Tests Unitaires - Système de Gestion des Congés

## Structure des Tests

Les tests unitaires du backend sont organisés comme suit :

```
src/test/java/com/odiak.leaveManagement.backend/
├── services/
│   ├── UserServiceTest.java
│   └── LeaveRequestServiceTest.java
├── controller/
│   ├── UserControllerTest.java
│   └── LeaveRequestControllerTest.java
├── integration/
│   ├── UserControllerIntegrationTest.java
│   └── LeaveRequestControllerIntegrationTest.java
├── e2e/
│   ├── pages/
│   │   ├── HomePage.java
│   │   ├── LeaveFormPage.java
│   │   └── LeaveListPage.java
│   ├── BaseTest.java
│   └── LeaveRequestE2ETest.java
└── BaseIntegrationTest.java
```

## Dépendances de Test

Les tests utilisent les dépendances suivantes (incluses dans `spring-boot-starter-test`) :

- **JUnit 5** - Framework de test
- **Mockito** - Mock des dépendances
- **Spring Test** - Support des tests Spring
- **AssertJ** - Assertions fluides (optionnel)

## Tests Implémentés

### 1. **UserServiceTest**
Tests unitaires du service utilisateur :
- ✅ Créer un utilisateur
- ✅ Récupérer tous les utilisateurs
- ✅ Récupérer un utilisateur par ID
- ✅ Mettre à jour un utilisateur
- ✅ Supprimer un utilisateur
- ✅ Gestion des erreurs (utilisateur non trouvé)

### 2. **LeaveRequestServiceTest**
Tests unitaires du service de demande de congé :
- ✅ Récupérer toutes les demandes
- ✅ Récupérer une demande par ID
- ✅ Créer une demande
- ✅ Mettre à jour une demande
- ✅ Supprimer une demande
- ✅ Trouver par utilisateur
- ✅ Trouver par statut
- ✅ Gestion des erreurs

### 3. **UserControllerTest**
Tests des endpoints REST utilisateur :
- ✅ GET `/api/users/all` - Récupérer tous les utilisateurs
- ✅ GET `/api/users/{id}` - Récupérer un utilisateur
- ✅ POST `/api/users` - Créer un utilisateur
- ✅ PUT `/api/users/{id}` - Mettre à jour un utilisateur
- ✅ DELETE `/api/users/{id}` - Supprimer un utilisateur

### 4. **LeaveRequestControllerTest**
Tests des endpoints REST demande de congé :
- ✅ GET `/api/leave-requests/all` - Récupérer toutes les demandes
- ✅ GET `/api/leave-requests/{id}` - Récupérer une demande
- ✅ POST `/api/leave-requests` - Créer une demande
- ✅ PUT `/api/leave-requests/{id}` - Mettre à jour une demande
- ✅ DELETE `/api/leave-requests/{id}` - Supprimer une demande

### 5. **Tests d'Intégration**
Vérification du flux de données complet avec base de données réelle (H2 en test) :
- ✅ `UserControllerIntegrationTest`
- ✅ `LeaveRequestControllerIntegrationTest`

### 6. **Tests de Bout-en-Bout (E2E) - Selenium**
Automatisation de l'interface utilisateur avec Selenium WebDriver :
- ✅ `LeaveRequestE2ETest` - Flux complet de demande de congé.
- ✅ `EmployeeE2ETest` - Vérification de la liste et des informations des employés.
- ✅ `ComprehensiveE2ETest` - Flux de travail complet sur tout le site (Navigation + CRUD).
- Utilise le **Page Object Model (POM)** pour une meilleure maintenance.
- Gère les attentes explicites (**Explicit Waits**) pour la réactivité d'Angular.

---

## Exécution des Tests

### Tous les tests
```bash
mvn test
# ou sur Windows
mvn.cmd test
```

### Tests spécifiques
```bash
mvn test -Dtest=UserServiceTest
mvn test -Dtest=LeaveRequestE2ETest
mvn test -Dtest=EmployeeE2ETest
mvn test -Dtest=ComprehensiveE2ETest
```

### Exécution des tests Selenium (E2E)
Pour exécuter les tests E2E, assurez-vous que :
1. Le backend est lancé.
2. Le frontend est lancé sur `http://localhost:4200`.
3. Un navigateur Chrome est disponible.

```bash
mvn test -Dtest=ComprehensiveE2ETest
```

## Rapports de Test

Après l'exécution des tests, les résultats sont disponibles dans :

```
backend/target/
├── surefire-reports/     # Rapports texte
└── site/jacoco/          # Couverture de code (si jacoco configuré)
```

## Ajouter des Tests de Couverture

Pour ajouter JaCoCo (couverture de code), ajoutez au `pom.xml` :

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.8</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## Bonnes Pratiques Utilisées

1. ✅ **Annotations @DisplayName** - Noms lisibles des tests
2. ✅ **Setup avec @BeforeEach** - Initialisation des données de test
3. ✅ **Mocking** - Isolation des composants
4. ✅ **Vérification** - `verify()` pour vérifier les interactions
5. ✅ **Assertions claires** - Messages d'erreur explicites
6. ✅ **Tests de cas d'erreur** - Gestion des exceptions

## Prochaines Étapes

1. Exécuter les tests : `mvn test`
2. Ajouter des tests pour les repositories
3. Ajouter la couverture de code avec JaCoCo
4. Intégrer les tests en CI/CD
