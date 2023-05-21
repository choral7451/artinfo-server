import {Injectable, UnauthorizedException} from '@nestjs/common';
import { PassportStrategy} from '@nestjs/passport';
import { ExtractJwt, Strategy } from 'passport-jwt';
import {UserService} from "@/apis/user/user.service";
import {UserStatus} from "@/apis/integration/type/enum";

@Injectable()
export class JwtAuthGuard extends PassportStrategy(Strategy, 'user') {
  constructor(
    private readonly userService: UserService,
  ) {
    super({
      jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
      ignoreExpiration: false,
      secretOrKey: process.env.JWT_TOKEN_KEY,
    });
  }

  async validate(payload: any) {
    const user = await this.userService.getUserById(payload.signature.id);

    if (user == null || user.status == UserStatus.DELETE) {
      throw new UnauthorizedException();
    }

    return payload.signature;
  }
}
