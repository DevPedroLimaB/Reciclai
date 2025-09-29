const express = require('express');
const router = express.Router();

router.get('/profile', (req, res) => {
  res.json({
    success: true,
    data: {
      id: '1',
      name: 'Usuário Teste',
      email: 'test@example.com',
      points: 250,
      level: 3
    }
  });
});

router.get('/stats', (req, res) => {
  res.json({
    success: true,
    data: {
      totalRecycled: 15.5,
      co2Saved: 3.2,
      pointsEarned: 250,
      schedulesCompleted: 8
    }
  });
});

module.exports = router;