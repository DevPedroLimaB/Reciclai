const express = require('express');
const router = express.Router();

router.get('/', (req, res) => {
  const rewards = [
    {
      id: '1',
      title: 'Desconto 10% Loja Verde',
      description: 'Ganhe 10% de desconto em produtos sustentáveis',
      pointsCost: 100,
      category: 'Desconto',
      available: true
    },
    {
      id: '2',
      title: 'Garrafa Reutilizável',
      description: 'Garrafa térmica ecológica',
      pointsCost: 250,
      category: 'Produto',
      available: true
    }
  ];
  
  res.json({
    success: true,
    data: rewards
  });
});

router.post('/:id/redeem', (req, res) => {
  res.json({
    success: true,
    message: 'Recompensa resgatada com sucesso!'
  });
});

module.exports = router;