const express = require('express');
const router = express.Router();

router.get('/', (req, res) => {
  const { lat, lng, radius } = req.query;
  
  const collectPoints = [
    {
      id: '1',
      name: 'EcoPonto Shopping Center',
      address: 'Av. Principal, 1000',
      latitude: -23.5505,
      longitude: -46.6333,
      acceptedMaterials: ['Papel', 'Plástico', 'Metal'],
      operatingHours: '08:00 - 18:00',
      phone: '(11) 99999-9999'
    },
    {
      id: '2', 
      name: 'Centro de Reciclagem Verde',
      address: 'Rua da Natureza, 500',
      latitude: -23.5615,
      longitude: -46.6565,
      acceptedMaterials: ['Vidro', 'Eletrônicos', 'Pilhas'],
      operatingHours: '07:00 - 17:00',
      phone: '(11) 88888-8888'
    }
  ];
  
  res.json({
    success: true,
    data: collectPoints
  });
});

module.exports = router;