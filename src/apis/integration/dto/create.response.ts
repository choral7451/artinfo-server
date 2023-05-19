import {Field, ObjectType} from "@nestjs/graphql";

@ObjectType()
export class CreateResponse {
  @Field(() => String)
  id: string;

  static fromId(id: string): CreateResponse {
    const response = new CreateResponse();
    response.id = id;
    return response;
  }
}
