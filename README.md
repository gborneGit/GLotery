
<p align="center">
<img src="https://github.com/gborneGit/gborneGit/blob/main/EcoCraft_logo_transparent.png" width="100"/>
</p>

# Description et configuration du plugin Minecraft pour EcoCraft

Le plugin Minecraft pour le serveur EcoCraft est un ajout qui permet aux joueurs d'interagir avec un coffre de loterie, en utilisant des clefs pour obtenir des récompenses aléatoires. Le plugin est configuré pour utiliser un coffre spécifique, dont la position est définie dans le fichier de configuration.

## Configuration:

```yaml
chest:
  world: 'world_safe'
  location: '7,106,26'
  items:
  '0':
    item: 'IRON_SWORD'
    name:
    lore:
    enchant: 'DURABILITY:1'
    prob: 10

[...]

  '10':
    item: 'IRON_HELMET'
    prob: 10
  '11':
    item: 'DIAMOND_HELMET'
    prob: 3
  '12':
    item: 'NETHERITE_HELMET'
    prob: 1
  '13':
    item: 'BOW'
    enchant:
      'ARROW_INFINITE:1'
      'DURABILITY:2'
    prob: 2
```

Chaque élément dans la liste "items" correspond à un objet que le joueur peut gagner lorsqu'il utilise une clé pour ouvrir le coffre de loterie. Les informations pour chaque objet sont fournies sous la forme de clés et de valeurs. Les clés "item" et "money" sont mutuellement exclusives, donc chaque objet ne peut contenir qu'une seule de ces clés. Si la clé "item" est utilisée, la clé "quantity" peut être incluse pour spécifier le nombre d'articles à donner au joueur. La clé "prob" indique la probabilité que cet objet soit gagné lors de l'ouverture du coffre. La clé "name" peut être utilisée pour spécifier un nom personnalisé pour l'objet, et la clé "lore" peut être utilisée pour fournir une description détaillée de l'objet. La clé "enchant" peut être utilisée pour ajouter des enchantements à l
