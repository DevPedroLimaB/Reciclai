const express = require('express');
const router = express.Router();

router.get('/', (req, res) => {
  res.json({
    success: true,
    data: [
      {
        id: '1',
        collectPointId: '1',
        scheduledDate: '2024-01-15',
        timeSlot: '10:00 - 12:00',
        materials: ['Papel', 'Plástico'],
        status: 'CONFIRMED'
      }
    ]
  });
});

router.post('/', (req, res) => {
  const { collectPointId, scheduledDate, timeSlot, materials } = req.body;
  
  res.status(201).json({
    success: true,
    message: 'Agendamento criado com sucesso',
    data: {
      id: Date.now().toString(),
      collectPointId,
      scheduledDate,
      timeSlot,
      materials,
      status: 'PENDING'
    }
  });
});

module.exports = router;