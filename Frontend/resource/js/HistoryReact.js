class Histories extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <table class="table table-bordered table-striped table-hover">
        <thead>
          <tr>
            <th>#</th>
            <th>License plate</th>
            <th>Brand</th>
            <th>Model code</th>
            <th>Date</th>
            <th>Time</th>
            <th>In or out</th>
            <th>Image</th>
          </tr>
        </thead>
        <tbody>
          {this.props.histories.map((history,index) => {
            return <History history={history} index={index}/>;
          })}
        </tbody>
      </table>
    );
  }
}

function History(props) {
  let numberPlate = props.history.vehicle.numberPlate;
  let firm = props.history.vehicle.carManufacturer;
  let kind = props.history.vehicle.nameVehicle;
  let date = new Date(props.history.time);
  let dateString = date.toLocaleDateString();
  let timeString = date.toLocaleTimeString();
  let image = props.history.image;
  image = domain + history_image + "/"+ image;
  let inOrOut = "IN";
  if (props.history.out) {
    inOrOut = "OUT";
  }
  let index = props.index +1;
  return (
    <tr>
      <td>{index}</td>
      <td>{numberPlate}</td>
      <td>{firm}</td>
      <td>{kind}</td>
      <td>{dateString}</td>
      <td>{timeString}</td>
      <td>{inOrOut}</td>
      <td><a className='history-image' href={image}>See</a></td>
    </tr>
  );
}
