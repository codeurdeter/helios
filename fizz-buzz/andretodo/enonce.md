# FocusList — Todo REST API (Spring Boot)

## Objectif

Développer une **API REST** en **Java avec Spring Boot** permettant de gérer une liste de tâches (*todos*).

Objectifs qualité :
- approche **TDD**
- **Clean Architecture**
- domaine métier **indépendant de Spring**
- code lisible et explicable à l’oral

Stockage **in-memory**.
Persistance (JSON / DB) en bonus.

---

## Contraintes techniques

- Java 17+
- Spring Boot
- JUnit 5
- MockMvc / SpringBootTest
- Pas d’authentification
- Pas de base de données obligatoire

---

## LISTE COMPLÈTE DES ROUTES

| Méthode | Route | Description |
|------|------|------------|
| POST | `/todos` | Créer une tâche |
| GET | `/todos` | Lister les tâches |
| GET | `/todos/{id}` | Détail d’une tâche |
| POST | `/todos/{id}/done` | Marquer comme terminée |
| POST | `/todos/{id}/reopen` | Réouvrir une tâche |
| DELETE | `/todos/{id}` | Supprimer une tâche |
| GET | `/stats` | Statistiques globales (bonus) |
| GET | `/help` | Documentation des routes |

---

## ROUTES – DÉTAIL COMPLET

---

## POST /todos

Créer une nouvelle tâche.

### Request Body

```json
{
  "title": "Préparer test pair programming",
  "priority": "HIGH",
  "dueDate": "2026-03-05",
  "tags": ["java", "tdd"]
}